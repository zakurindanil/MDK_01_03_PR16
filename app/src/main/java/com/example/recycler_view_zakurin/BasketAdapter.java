package com.example.recycler_view_zakurin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    public iOnClickInterface Delete, Cost; // Добавляем обработчики событий для удаления элемента и рассчёта стоимости
    public LayoutInflater Inflater; // класс позволяющий из содержимого создать View элементы
    public ArrayList<Basket> BasketItems; // список объектов добавленных в корзинуы
    // конструтор
    public BasketAdapter(Context context, ArrayList<Basket> basketItems, iOnClickInterface delete, iOnClickInterface cost) {
        this.Inflater = LayoutInflater.from(context); // инициализируем
        this.BasketItems = basketItems; // запоминаем список товаров добавленных в корзину
        this.Delete = delete; // запоминаем метод который будет вызываться при удалении
        this.Cost = cost; // запоминаем метод, который будет пересчитывать корзину
    }
    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // Создание View
        View view = Inflater.inflate(R.layout.item_basket, parent, false); // создаём View из свёрстаного элемента
        return new ViewHolder(view); // возвращаем
    }
    @Override
    public void onBindViewHolder(BasketAdapter.ViewHolder holder, int position) { // установка значений
        Basket Item = BasketItems.get(position); // получаем объект в корзине

        holder.tvName.setText(Item.Item.Name); // присваиваем имя
        holder.tvPrice.setText("₽" + String.valueOf(Item.Item.Price)); // устанавливаем значение цены
        holder.tvCount.setText(String.valueOf(Item.Count)); // установка значения количества

        holder.bthPlus.setOnClickListener(new View.OnClickListener() { // при нажатии на плюс
            @Override
            public void onClick(View view) {
                Item.Count++; // увеличиваем количество
                holder.tvCount.setText(String.valueOf(Item.Count)); // изменяем значение текстового поля
                Cost.setClick(view, position); // выполняем перерасчёт цены
            }
        });
        holder.bthMinus.setOnClickListener(new View.OnClickListener() { // при нажатии на минус
            @Override
            public void onClick(View view) {
                Item.Count--; // уменьшаем количество
                holder.tvCount.setText(String.valueOf(Item.Count)); // изменяем значение текстового поля
                Cost.setClick(view, position); // выполняем перерасчёт цены
            }
        });
        holder.bthDelete.setOnClickListener(new View.OnClickListener() { // при удалении элемента
            @Override
            public void onClick(View view) {
                Delete.setClick(view, position); // удаляем элемент
                Cost.setClick(view, position); // выполняем перерасчёт цены
            }
        });
    }

    @Override
    public int getItemCount() {
        return BasketItems.size();
    } // метод для получения количества элементов

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPrice, tvCount; // текстовые поля, наименование, цена и количество
        public ImageView bthPlus, bthMinus; // кнопки + и -
        public LinearLayout bthDelete; // кнопка удаления

        ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name); // находим наименование на вёрстке
            tvPrice = view.findViewById(R.id.tv_price); // находим стоимость на вёрстке
            tvCount = view.findViewById(R.id.tv_count); // находим количество на вёрстке
            bthPlus = view.findViewById(R.id.bthPlus); // находим + на вёрстке
            bthMinus = view.findViewById(R.id.bthMinus); // находим - на вёрстке
            bthDelete = view.findViewById(R.id.ll_delete); // находим кнопку удалить на вёрстке
        }
    }
}
