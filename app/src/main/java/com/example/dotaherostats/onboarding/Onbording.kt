package com.example.dotaherostats.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.ListPopupWindow.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dotaherostats.Login
import com.example.dotaherostats.R
import com.example.dotaherostats.databinding.ActivityOnbordingBinding

class Onbording : AppCompatActivity() {

    private lateinit var binding: ActivityOnbordingBinding
    private lateinit var indicatorsContainer: LinearLayout
    private lateinit var onboardingAdapter:OnboardingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnbordingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onboardingAdapter = OnboardingAdapter(
            //Se crea  y se pasa los datos del Onboarding(imagen , titulo , descricion) al adapter
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.onboarding,
                    title = "Updated Heroes List",
                    description = "check all the detailed list of heroes, stats, powers, matchups, etc. "
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.onboarding_2,
                    title = "choose your favorites",
                    description = "select and filter your favorite heroes and have them in a separate section"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.onboarding_3,
                    title = "save it in the cloud   ",
                    description = "save and download images from the google cloud "
                )
            )

        )

        indicatorsContainer = binding.indicatorConteiner
        setUpIndicators()
        setCurrentIndicator(0)
        binding.onboardingViewPager.adapter =onboardingAdapter


        //aca ponesmos un Listener para escuchar cuando en el ViewPager se registre un cambio
        //en este caso cunado se cambia de pagina
        binding.onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //a la funcion creadad le pasamos la position que es la pagina
                //actual donde esta el viewPager
                setCurrentIndicator(position)
            }
            })

        //con esto evitamos que al momento de pasar de paginas en el viewPager
        //nos pasemos del final de la pagina
        (binding.onboardingViewPager.getChildAt(0)as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        indicatorsContainer = findViewById(R.id.indicatorConteiner)


        binding.btnGetStardOnbording.setOnClickListener {
            navigateToMain()
        }

        binding.imageNext.setOnClickListener {
            if(binding.onboardingViewPager.currentItem + 1  < onboardingAdapter.itemCount){
                binding.onboardingViewPager.currentItem += 1
            }else{
                navigateToMain()
            }
        }

        binding.txtSkip.setOnClickListener {
            navigateToMain()
        }


    }


    //logica para el indicador de la posicion actual de onboarding situado en la parte superion izquierda del onboarding
    private fun setUpIndicators(){

        //obtine la cantidad de items en nuestro adaptador de ONBOARDING = 3
        // que sera guardado en nuestro array  de Vista
        // por lo tanto tendremos 3 Onboarding
        val indicators = arrayOfNulls<ImageView>( onboardingAdapter.itemCount)

        // se configura  el linearlout donde estara los indicadores sus tamaño
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        // y sus margenes
        layoutParams.setMargins(8,0,8,0)

        //con un bucle recorremos todos los indices de nuestro  array indicators queson 3
        for (i in indicators.indices){
            //le decimos que recivira un imageview
            indicators[i] = ImageView(applicationContext)

            //y le seteamos los images view guardados en nuestro drawble a cada indice
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_bacground
                    )
                )
                //le seteamos lo parametros antes creados
                it.layoutParams=layoutParams
                // y le añadimos la vista
                indicatorsContainer.addView(it)
            }
        }
    }

    //SELECCIONA LA POSICION ACTUAL DEL  INDICADOR Y LO ACTIVA O DESACTIVA
    private fun setCurrentIndicator(position:Int){
        // childCoundt nos dice cuantas  View hijas de IndicatorContainer tenemos en este caso 3
        val childCount = indicatorsContainer.childCount

        //dentro del for se hace un recorrido de las posiciones childcound 1-3
        //y si la posicion coincida con el numero actual que se envia como paramtro se muestra
        //la view  de indicator_active_bagcroudn
        for(i in  0 until childCount){
            val   imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_bacground
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_bacground
                    )
                )
            }
        }
    }

    private fun navigateToMain(){
        var intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

}