package com.example.suelliton.mcpd_android.adapters;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suelliton on 02/10/2017.
 */

public class PermissionUtils {
    /**
     * Solicita as permissões
     */
    public static boolean validate(Activity activity, int requestCode, String... permissions) {

        List<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            //verifica se tem a permissão
            if (ContextCompat.checkSelfPermission( activity, permission) != PackageManager.PERMISSION_GRANTED) {
                // verifica se alguma vez o usuário já negou permissão e precisamos dar outra explicação
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Toast.makeText(activity, "Por favor, omi", Toast.LENGTH_SHORT).show();
                    permissionsToRequest.add(permission);

                } else {
                    // No explanation needed, we can request the permission.
                    permissionsToRequest.add(permission);
                }
            }
        }

        if (permissionsToRequest.isEmpty()) {
            // Tudo ok, retorna true
            return true;
        }

        // Lista de permissões que falta acesso.
        String[] newPermissions = new String[permissionsToRequest.size()];
        permissionsToRequest.toArray(newPermissions);

        // Solicita permissão
        ActivityCompat.requestPermissions(activity, newPermissions, 1);

        return false;
    }
}
