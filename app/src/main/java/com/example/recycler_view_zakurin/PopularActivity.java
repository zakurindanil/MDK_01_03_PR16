package com.example.recycler_view_zakurin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PopularActivity extends AppCompatActivity {

    RecyclerView CategoryList, CardList; // RecyclerView категорий и предметов
    TextView TvNamePage; // Наименование страницы
    CategoryAdapter CategoryAdapter; // адаптер категорий
    ArrayList<Category> Categorys = new ArrayList<>(); // список категорий
    ArrayList<Item> Items = new ArrayList<>(); // список предметов
    public Context Context; // контекст

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        Context = this; // запоминаем контекст
        Categorys = CategoryContext.All(); // получаем категории

        Bundle arguments = getIntent().getExtras(); // получаем данные которые были переданы на активность
        // получаем Id категории
        Integer IdCategory = Integer.valueOf(arguments.get("Category").toString());

        CategoryList = findViewById(R.id.category_list); // получаем recyclerView для категорий
        CardList = findViewById(R.id.card_list); // получаем recyclerView для предметов
        TvNamePage = findViewById(R.id.tv_name_page); // получаем наименование страницы

        if(IdCategory != -1) { // если выбрана категория
            Category SelectCategory = Categorys.stream() // получаем выбранную категорию
                    .filter(item -> item.Id == IdCategory) // по фильтру
                    .findAny() // первое значение
                    .orElse(null); // или нулевое
            SelectCategory.Active = true; // изменяем статус категории
            TvNamePage.setText(SelectCategory.Name); // отображаем наименование

            CategoryAdapter = new CategoryAdapter(this, Categorys, CategoryClick); // создаём адаптер категорий
            CategoryList.setAdapter(CategoryAdapter); // устанавливаем адаптер
        } else {
            CategoryList.setVisibility(View.GONE); // если категория не пришла - скрываем категории

            TextView TvNameCategory = findViewById(R.id.tv_name_category); // получаем слово "Категории"
            TvNameCategory.setVisibility(View.GONE); // скрываем его
        }
        // если категория не пришла, получаем все товары, если пришла, только те которые относятся к категории
        Items = IdCategory == -1 ? ItemContext.All() : ItemContext.GetByCategory(IdCategory);
        CardList.setLayoutManager(new GridLayoutManager(this, 2)); // задаём два столбца для товаров
        ItemAdapter CardAdapter = new ItemAdapter(this, Items, MainActivity.init.AddBasker); // создём адаптер для товаров
        CardList.setAdapter(CardAdapter); // устанавливаем адаптер
    }

    iOnClickInterface CategoryClick = new iOnClickInterface() { // при нажатии на категорию
        @Override
        public void setClick(View view, int position) {
            for (Category Item : Categorys) // перебираем все категории
                Item.Active = false; // обнуляем статус категории

            Category SelectCategory = Categorys.get(position); // получаем выбранную категорию
            SelectCategory.Active = true; // изменяем статус категории
            CategoryList.setAdapter(CategoryAdapter); // применяем адаптер для обновления значений

            TvNamePage.setText(SelectCategory.Name); // меняем наименование страницы

            Items = ItemContext.GetByCategory(SelectCategory.Id); // получаем товары категории
            ItemAdapter CardAdapter = new ItemAdapter(Context, Items, MainActivity.init.AddBasker); // создаём адаптер с новыми товарами
            CardList.setAdapter(CardAdapter); // применяем адаптер
        }
    };

    public void ClosePopularActivity(View view) { finish(); } // при закрытии активности, закрываем
}
