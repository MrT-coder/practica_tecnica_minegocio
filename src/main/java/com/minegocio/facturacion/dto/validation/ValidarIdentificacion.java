package com.minegocio.facturacion.dto.validation;

import org.springframework.stereotype.Component;

@Component  // Marca esta clase como un componente gestionado por Spring
public class ValidarIdentificacion {
    public boolean esRucValido (String numeroIdentificacion){
        if (numeroIdentificacion == null || numeroIdentificacion.length() != 13){
            return false; // RUC debe tener exactamente 13 dígitos
        }

        //Verificamos que tenga solo dígitos
        if(!numeroIdentificacion.matches("\\d{13}")){
            return false; // RUC debe contener solo dígitos
        }

        return numeroIdentificacion.endsWith("001"); // RUC debe terminar en '001'
    }

    public boolean esCedulaValida (String numeroIdentificacion){
        if (numeroIdentificacion == null || numeroIdentificacion.length() != 10){
            return false; // Cédula debe tener exactamente 10 dígitos
        }

        //Verificamos que tenga solo dígitos
        if(!numeroIdentificacion.matches("\\d{10}")){
            return false; // Cédula debe contener solo dígitos
        }

        return true;

    }
}
