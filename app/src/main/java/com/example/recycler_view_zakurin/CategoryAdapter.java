package com.example.recycler_view_zakurin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private iOnClickInterface OnClickInterface; // обработчик события который будет срабатывать в активности
    private final LayoutInflater Inflater; // класс позволяющий из содержимого, создать View элемент
    private final List<Category> Categorys; // список категорий
    private Drawable BackgroundSelect; // задний фон, выбранной категории
    // конструктор
    CategoryAdapter(Context context, List<Category> categorys, iOnClickInterface onClickInterface) {
        this.Inflater = LayoutInflater.from(context); // инициализируем
        this.Categorys = categorys; // получаем категории, и запоминаем их в переменную

        BackgroundSelect = ContextCompat.getDrawable(context, R.drawable.item_category_background_select); // получаем файл заднего фона
        this.OnClickInterface = onClickInterface; // получаем обработчик события который будет выполнятся
    }
    // создание View
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = Inflater.inflate(R.layout.item_category, parent, false); // создаём View из свёрстанного элемента
        return new ViewHolder(view); // возвращаем
    }
    // установка значений
    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        Category Category = Categorys.get(position); // получаем данные из списка, для каждой View, свои данные
        holder.tvName.setText(Category.Name); // устанавливаем текст в наименование категории
        holder.parent.setOnClickListener(new View.OnClickListener() { // при нажатии на элемент
            @Override
            public void onClick(View view) {
                // вызываем обработчик событий, передавая элемент на который нажали, и его позицию
                OnClickInterface.setClick(holder.parent, position);
            }
        });
        if(Category.Active) { // если категория активна
            holder.parent.setBackground(BackgroundSelect); // устанавливаем задний фон
            holder.tvName.setTextColor(Color.WHITE); // меняем цвет текста
        }
    }

    @Override
    public int getItemCount() {
        return Categorys.size();
    } // метод для получения количества элементов

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout parent; // родительский элемент
        final TextView tvName; // текстовове поле

        ViewHolder(View view) {
            super(view);
            parent = view.findViewById(R.id.parent); // получаем родительский элемент
            tvName = view.findViewById(R.id.tv_name); // получаем родительское поле
        }
    }
}
