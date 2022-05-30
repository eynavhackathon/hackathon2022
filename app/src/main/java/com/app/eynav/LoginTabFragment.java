package com.app.eynav;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginTabFragment  extends Fragment {
    EditText email,pass;
    Button btn_login;
    float v = 0;
    String userType;
    ProgressDialog pd;
    private FirebaseAuth mAuth;

    public LoginTabFragment(String userType){
        this.userType = userType;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tap_fragment, container, false);
        email= root.findViewById(R.id.email);
        pass= root.findViewById(R.id.pass);
        btn_login= root.findViewById(R.id.btn_login);
        pd = new ProgressDialog(getContext());
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(l ->{
            String email1 = email.getText().toString();
            String passw = pass.getText().toString().trim();
            //validate
            if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                //invalid email pattern, set error
                email.setError("Invalid Email");
                email.setFocusable(true);
            }
            else{
                LoginUser(email1, passw);
            }

        });
        email.setTranslationX(800);
        email.setAlpha(v);
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.setTranslationX(800);
        pass.setAlpha(v);
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btn_login.setTranslationX(800);
        btn_login.setAlpha(v);
        btn_login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        return root;
    }

    private void LoginUser(String email, String passw) {
        pd.setMessage("Logging in...");
        //show progress dialog
        pd.show();
        mAuth.signInWithEmailAndPassword(email, passw)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //dismiss progress dialog
                            pd.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //user is logged in, so start login activity
                            Intent intent = new Intent(getContext(),MainActivity.class);
                            intent.putExtra("userType",userType);
                            startActivity(intent);
                        } else {
                            //dismiss progress dialog
                            pd.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //dismiss progress dialog
                        pd.dismiss();
                        //error, gat and show error message
                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
