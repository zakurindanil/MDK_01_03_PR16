package com.example.recycler_view_zakurin;

import java.util.ArrayList;

public class CategoryContext {

    public static ArrayList<Category> All() { // метод возвращающий список категорий
        ArrayList<Category> Categorys = new ArrayList<>(); // список категорий

        Categorys.add(new Category(0, "Все")); // добавляем категорию
        Categorys.add(new Category(1, "Outdoor")); // добавляем категорию
        Categorys.add(new Category(2, "Tennis")); // добавляем категорию
        Categorys.add(new Category(3, "Running")); // добавляем категорию

        return Categorys; // возвращаем список категорий
    }
}
