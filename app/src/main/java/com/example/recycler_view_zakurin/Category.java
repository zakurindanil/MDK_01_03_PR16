package com.example.recycler_view_zakurin;

public class Category {

    public int Id; // Код категории
    public String Name; // наименование категории
    public Boolean Active = false; // Активность категории
    // Конструктор, принимающий значения
    public Category(int id, String name) {
        this.Id = id; // код
        this.Name = name; // наименование
    }
}
