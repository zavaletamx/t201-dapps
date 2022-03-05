package mx.edu.uteq.dapps.holamundo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.Toast;

import mx.edu.uteq.dapps.holamundo.databinding.ActivityValidacionesBinding;

public class ValidacionesActivity extends AppCompatActivity {

    private ActionBar actionBar;

    /*
    Podemos realizar una vinculación automática de los elementos de la vista
    con los componentes del controlador
    utilizando la librería ViewBinding

    Para ello debemos activarla en la configuración del proyecto

    ViewBinding se activa desde el archivo app.gradle

    Binding genera una clase intermediaria entre el Controlador y la vista
    con el nombre:
    Activity/Fragment{NOMBRE_CLASE}Binding
    Ejem:
    FragmentInicioBinding
    ActivityValidacionesBinding
     */
    private ActivityValidacionesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Indicamos que vamos a mostrar la vista que binding
        interprete a partir del contexto
         */
        binding = ActivityValidacionesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*
        Usando viewBinding YA NO ES NECESARIO LIGAR LOS COMPONENTES
        mediante findViewById

        Binding permite invocar el identificador del elemento de la vista
         desde el controlador utilizando la definicón del ID convirtiendolo
         a notación camello
         VISTA              CONTROLADOR
         btn_registro       binding.btnRegistro
         til_nombre         binding.tilNombre
         tiet_email         binding.tietEmail
         */

        /*
        Validamos lo que se va escribiendo en el campo de nombre
         */
        binding.tietNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                /* Este método se ejecuta cada que se escribr algo */
                validarNombre(editable.toString());
            }
        });

        /*
        Validamos el correo electrónico mientras se escribe
         */
        binding.tietEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                /*
                Invocamos al método de validar correo cuando esté escribiendo
                la variable (parámetro) 'editable' contiene el texto conforme se
                va escribiendo
                */
                validarCorreo(editable.toString());
            }
        });

        /*
        Validamos el teléfono mientras se escribe
         */
        binding.tietTelefono.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                validarTelefono(editable.toString());
            }
        });

        /*
        Click del botón Registrarse
         */
        binding.mbValidar.setOnClickListener(view -> {
            /*
            Validamos a nombre
             */
            validarNombre(binding.tietNombre.getText().toString());

            /*
            Validamos el correo electrónico
             */
            validarCorreo(binding.tietEmail.getText().toString());

            /*
            Validamos el teléfono
             */
            validarTelefono(binding.tietTelefono.getText().toString());
        });

    }

    /**
     *
     * @param texto
     * @return
     */
    public boolean validarNombre(String texto) {
        /*
        Si el texto es menor a 5 caracteres, el nombre es inválido
         */
        if (texto.trim().length() < 8) {
            // Mostramos el error en el TextInputLayout
            binding.tilNombre.setError("Nombre inválido, agrega un nombre de 8 letras como mínimo");
            //Quitamos el icono de error
            //binding.tilNombre.setErrorIconDrawable(null);
            return false;
        }

        else {
            binding.tilNombre.setErrorEnabled(false);
            return true;
        }
    }

    /**
     *
     * @param texto
     * @return
     */
    public boolean validarCorreo(String texto) {
        /*
        Utilizamos la librería android.Partterns para evaluar si un texto tiene
        el formato de un correo electrónico
         */
        if (Patterns.EMAIL_ADDRESS.matcher(texto).matches()) {
            //Elimanos el error
            binding.tilEmail.setErrorEnabled(false);

            return true;
        }

        //Si no es un formato válido de correo electrónico
        else {
            //Indicamos el error
            binding.tilEmail.setError("Ingresa un correo electrónico válido");
            //quitamos el icono de error
            //binding.tilEmail.setErrorIconDrawable(null);
            return false;
        }
    }

    /**
     *
     * @param texto
     * @return
     */
    public boolean validarTelefono(String texto) {
        /*
        Validamos con Patterns
        Si es un teléfono a 10 dígitos SIN ESPACIOS ni puntos ni guiones
        utilizamos el método de comparación contains que retorna verdadero
        cuanod una cadena de texto contiene el caracter indicado en cualquier
        posición
        "Hola m", "Holam ", " Holam"
        CADENA.contains(" ") ===> Verdadero si existe en cualquier pos.

        !CADENA.contains(" ") ===> Verdadero si NO existe en cualquier pos.
        El símbolo "!" indica el inverso o negación
         */

        if (
                Patterns.PHONE.matcher(texto).matches() &&
                texto.length() == 10 &&
                !texto.contains(" ") &&
                !texto.contains("-") &&
                !texto.contains(".")
        ) {
            binding.tilTelefono.setErrorEnabled(false);
            return true;
        }

        else {
            binding.tilTelefono.setError("Ingresa un teléfono válido a 10 dígitos");
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}