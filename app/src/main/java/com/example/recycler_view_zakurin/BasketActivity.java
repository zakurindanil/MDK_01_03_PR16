package com.example.recycler_view_zakurin;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class BasketActivity extends AppCompatActivity {

    public iOnClickInterface Delete = new iOnClickInterface() { // событие удаления товара в корзине
        @Override
        public void setClick(View view, int position) {
            MainActivity.init.BasketList.remove(position); // обращаемся к корзине, и удаляем элемент
            BasketRV.setAdapter(BasketAdapter); // обновляем адаптер
        }
    };
    public iOnClickInterface EventCost = new iOnClickInterface() { // событие перерасчёта стоимости
        @Override
        public void setClick(View view, int position) {
            CostCalculation(); // вызываем метод перерасчёта стоимости
        }
    };

    public RecyclerView BasketRV; // список элементов находящихся в корзине
    public TextView tvSum, tvAllSum; // текстовые поля для перерасчёта стоимости
    BasketAdapter BasketAdapter; // адаптер для отображения элементов в ресайкл вью
    Context Context; // ссылка на контекст данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        Context = this; // запоминаем контекст
        BasketRV = findViewById(R.id.basket_list); // находим ресайкл вью

        tvSum = findViewById(R.id.tv_sum); // находим текстовое поле суммы
        tvAllSum = findViewById(R.id.tv_all_sum); // находим текстовое поле всей суммы

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(SwipeAdapter); // инициализируем вспомогательный класс нажатий
        itemTouchHelper.attachToRecyclerView(BasketRV); // применяем вспомогательный класс нажатий для списка товаров

        BasketAdapter = new BasketAdapter(this, MainActivity.init.BasketList, Delete, EventCost); // инициализируем адаптер
        BasketRV.setAdapter(BasketAdapter); // назначаем адаптер для списка товаров находящегося в корзине

        CostCalculation(); // вызываем перерасчёт стоимости товаров
    }
    // обработчик событий для нажатий, разрешаем сайпы в лево и вправо
    ItemTouchHelper.SimpleCallback SwipeAdapter = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                RecyclerView.ViewHolder target) {

            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            BasketRV.setAdapter(BasketAdapter); // при полном свайпе, обновляем адаптер
        }

        @Override
        public void onChildDraw(
                Canvas c,
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                float dX,
                float dY,
                int actionState,
                boolean isCurrentlyActive) { // метод обрабатывающий перемещение обхекта

            Resources r = getResources(); // инициализируем ресурсы
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, r.getDisplayMetrics()); // переводим дюймы в пиксели

            LinearLayout bthDelete = viewHolder.itemView.findViewById(R.id.ll_delete); // находим кнопку удалить
            LinearLayout bthCount = viewHolder.itemView.findViewById(R.id.ll_count); // находим блок с количеством

            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) { // если состояние перемещение
                if(dX < -px) { // если перемещение меньше чем ширина кнопки удалить
                    bthDelete.setVisibility(View.VISIBLE); // включаем кнопку удалить
                    bthCount.setVisibility(View.GONE); // скрываем количество в корзине
                } else if(dX > px) { // если наоборот
                    bthDelete.setVisibility(View.GONE); // скрываем кнопку удалить
                    bthCount.setVisibility(View.VISIBLE); // показываем количество в корзине
                }
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public void CostCalculation() { // метод перерасчёта товаров в корзине
        float ItemPrice = 0; // создаём переменную
        for (Basket Item: MainActivity.init.BasketList) { // перебираем элементы в корзине
            ItemPrice += Item.Item.Price * Item.Count; // считаем стоимость
        }

        tvSum.setText("₽" + ItemPrice); // отображаем стоимость товаров
        ItemPrice += 60.20; // добавляем доставку
        tvAllSum.setText("₽" + ItemPrice); // отображаем итоговую стоимость
    }

    public void ClosePopularActivity(View view) { // метод закрытия активности
        finish(); // закрываем активность
    }
}
