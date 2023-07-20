package com.pedrolgsoares.organizzeclone;

import androidx.appcompat.app.AppCompatActivity;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import android.os.Bundle;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // não será mais necessário pois teraá o slider setContentView(R.layout.activity_main);
        // definir a visbilidadde dos botões de voltar e avançar
       /*
            setButtonBackVisible(false);
            setButtonNextVisible(false);

            addSlide(new SimpleSlide.Builder()
                    .title("R.string.title_1")
                    .description("R.string.description_1")
                    .image(R.drawable.um)
                    .background(R.color.black)
                    .build());

            addSlide(new SimpleSlide.Builder()
                    .title("R.string.title_1")
                    .description("R.string.description_1")
                    .image(R.drawable.dois)
                    .background(R.color.white)
                    .build());
        */
        // Com fragmente
        setButtonBackVisible(false);
        setButtonNextVisible(false);
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.fragment1)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.fragment2)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.fragment3)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.fragment4)
                .canGoForward(false)
                .build());

    }
}