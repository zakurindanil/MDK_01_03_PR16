package com.example.recycler_view_zakurin;

public class Basket {
    public Item Item; // предмет добавленный в корзину
    public Integer Count; // количество предмета

    public Basket(Item item, Integer count) { // конструктор
        this.Item = item; // запоминаем предмет
        this.Count = count; // запоминаем количество
    }
}
