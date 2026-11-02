package com.example.recycler_view_zakurin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Context Context; // ссылка на контекст
    public static MainActivity init; // ссылка на активность

    public ArrayList<Basket> BasketList = new ArrayList<>(); // Список предметов в корзине
    public ArrayList<Item> Items; // Список предметов

    public iOnClickInterface AddBasker = new iOnClickInterface() { // обработчик добавления элементов в корзину
        @Override
        public void setClick(View view, int position) { // при добавлении в корзину
            // Проверяем не добавлен ли наш элемент уже в корзину
            Basket Item = BasketList.stream()
                    .filter(item -> item.Item.Id == position)
                    .findAny()
                    .orElse(null);
            // Получаем элемент который необходимо добавить
            Item FindItem = Items.stream().filter(item -> item.Id == position)
                    .findAny()
                    .orElse(null);

            if(Item == null) { // Если элемент не добавлен
                Item = new Basket(FindItem, 1); // Создаём новый элемент, с количеством 1
                BasketList.add(Item); // Добавляем в массив
            }
            else // если элемент добавлен
                Item.Count++; // Увеличиваем количество

            Toast.makeText(Context, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show(); // отображаем сообщение
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context = this; // запоминаем контекст
        init = this; // инициализируем init


        ArrayList<Category> Categorys = CategoryContext.All(); // получаем категории
        Items = ItemContext.All(); // получаем товары

        RecyclerView CategoryList = findViewById(R.id.category_list); // получаем recyclerView для категорий
        RecyclerView CardList = findViewById(R.id.card_list); // получаем recyclerView для товаров

        CategoryAdapter CategoryAdapter = new CategoryAdapter(this, Categorys, Click); // Создаём адптер для категорий
        CategoryList.setAdapter(CategoryAdapter); // назначаем адаптер для категорий

        ItemAdapter CardAdapter = new ItemAdapter(this, Items, AddBasker); // Создаём адаптер для предметов
        CardList.setAdapter(CardAdapter); // назначаем адаптер для предметов

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); // создаём транзакцию для фрагмента
        MenuNavigation fragment = new MenuNavigation(); // инициплизируем фрагмент меню
        ft.add(R.id.menu_navigation, fragment); // доавляем фрагмент на экран
        ft.commit(); // запоминаем
    }

    public void OpenPopularView(View view) { // открытие популярных товаров
        Intent newIntent = new Intent(this, PopularActivity.class); // создаём интент
        newIntent.putExtra("Category", -1); // запоминаем категорию
        startActivity(newIntent); // открываем активность
    }

    iOnClickInterface Click = new iOnClickInterface() { // Создаём обработчик события при выборе категории
        @Override
        public void setClick(View view, int position) { // при нажатии на категорию
            Intent newIntent = new Intent(Context, PopularActivity.class); // создаём интент
            newIntent.putExtra("Category", position); // запоминаем категорию
            startActivity(newIntent); // открываем активность
        }
    };
}
