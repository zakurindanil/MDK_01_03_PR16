package com.example.recycler_view_zakurin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public iOnClickInterface AddBasket; // Событие вызываемое при добавлении элементов в корзину
    private LayoutInflater Inflater; // класс позволяющий из содержимого, создать View элемент
    private List<Item> Items; // список объектов

    public ItemAdapter(Context context, List<Item> items, iOnClickInterface addBasket) {
        this.Inflater = LayoutInflater.from(context); // инициализируем
        this.Items = items; // запоминаем объекты в переменную
        this.AddBasket = addBasket; // запоминаем событие чтобы вызвать его при добавлении элемента в корзину
    }

    // создание View
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = Inflater.inflate(R.layout.item_card, parent, false); // создаём View из свёрстанного элемента
        return new ItemAdapter.ViewHolder(view); // возвращаем
    }
    // установка значений
    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item Item = Items.get(position); // получаем данные из списка, для каждой View, свои данные
        holder.TvName.setText(Item.Name); // устанавливаем имя
        holder.TvModell.setText(Item.Modell); // устанавливаем модель
        holder.TvPrice.setText("₽" + String.valueOf(Item.Price)); // устанавливаем стоимость
        holder.bthAdd.setOnClickListener(new View.OnClickListener() { // при нажатии кнопки
            @Override
            public void onClick(View view) {
                AddBasket.setClick(view, Item.Id); // вызываем событие добавления элемента в корзину
            }
        });
    }

    @Override
    public int getItemCount() { return Items.size(); } // метод для получения количества элементов

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView TvName, TvModell, TvPrice; // текстовые поля для взаимодействия
        public LinearLayout bthAdd; // кнопка добавления элемента в корзину

        ViewHolder(View view) {
            super(view);
            TvName = view.findViewById(R.id.tv_name); // находим имя
            TvModell = view.findViewById(R.id.tv_modell); // находим модель
            TvPrice = view.findViewById(R.id.tv_price); // находим стоимость

            bthAdd = view.findViewById(R.id.bthAdd); // получаем кнопку
        }
    }
}
