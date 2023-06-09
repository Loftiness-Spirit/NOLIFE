package com.example.nolife;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nolife.databinding.FragmentLoginBinding;
import com.example.nolife.databinding.FragmentRegisterBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private ArticleViewModel mArticleViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRegisterBinding binding = FragmentRegisterBinding.inflate(inflater);
        mArticleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = binding.username.getText().toString();
                String nickname = binding.nickname.getText().toString();
                String password = binding.password.getText().toString();
                String repeat_pass = binding.repeatPassword.getText().toString();
                if(mail.isEmpty()||password.isEmpty()||nickname.isEmpty()||repeat_pass.isEmpty()
                || !password.equals(repeat_pass)){
                    Toast.makeText(getContext(), "Неправильно", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    mArticleViewModel.insertUser(new User(mail, nickname, password));
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("name", nickname);
                        startActivity(intent);
                    }
                }
        });
        return binding.getRoot();
    }
}