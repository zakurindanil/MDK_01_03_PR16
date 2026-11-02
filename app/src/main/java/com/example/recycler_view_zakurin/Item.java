package com.example.recycler_view_zakurin;

public class Item {
    public int Id; // код
    public String Name; // наименование
    public String Modell; // модель
    public Integer Price; // стоимость
    public Integer IdCategory; // код категории
    // конструктор
    public Item(String name, String modell, Integer price, Integer idCategory) {
        this.Name = name; // запоминаем имя
        this.Modell = modell; // запоминаем модель
        this.Price = price; // запоминаем стоимость
        this.IdCategory = idCategory; // запоминаем категорию
    }
}
