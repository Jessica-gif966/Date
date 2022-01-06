package com.example.nicedate;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Chat extends Fragment {
    View Chat;

    TextView message_send, message_receive;
    EditText inputEditText;
    Button sendButton;
    Context context;
    int mensaje=0;

    List<ListElemetns> elements;
    ListAdaptar listAdaptar;

    RecyclerView recyclerView,recyclerView2;

    public Chat() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Chat= inflater.inflate(R.layout.fragment_chat, container, false);

        context = getActivity();
        inputEditText = Chat.findViewById(R.id.inputEditText);
        sendButton = Chat.findViewById(R.id.sendButton);
        recyclerView = Chat.findViewById(R.id.chatView);

        elements= new ArrayList();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputEditText.getText().toString();
                /* message_send.append(Html.fromHtml("<p><b>Tu:</b> " + input + "</p>"));*/
                //recyclerView.setAdapter(listAdapter_mesaje_recivido);

                elements.add(new ListElemetns(input,"",mensaje));
                listAdaptar = new ListAdaptar(elements,getActivity());

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(listAdaptar);
                mensaje+=1;



                inputEditText.setText("");
            }
        });

        return Chat;
    }

    public void getResponse(String input,TextView response_view,Context context,List<ListElemetns> elements,int position) {


        String urlAssistant = "https://api.au-syd.assistant.watson.cloud.ibm.com/instances/b6dc3fce-9126-498a-92f2-34230eafbf88/v2/assistants/de63b2ec-80f1-48de-96b4-adc4646ab116/message?version=2021-06-14";
        String authentication = "YXBpa2V5OnptWGFyTTJPVTRfV0JiY0Fzb1dCbTBreHdCeHdFdmtxQXgtQVJOaU1zem5i";

        //creo la estructura json de input del usuario
        JSONObject inputJsonObject = new JSONObject();
        try {
            inputJsonObject.put("text",input);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("input", inputJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(urlAssistant)
                .addHeaders("Content-Type","application/json")
                .addHeaders("Authorization","Basic " + authentication)
                .addJSONObjectBody(jsonBody)
                .setPriority(Priority.HIGH)
                .setTag(context.getString(R.string.app_name))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String outputAssistant = "";

                        //parseo la respuesta del json
                        try {
                            JSONObject outputJsonObject = response.getJSONObject("output").getJSONArray("generic").getJSONObject(0);
                            outputAssistant=outputJsonObject.getString("text");

                            elements.set(position,new ListElemetns(input,outputAssistant,position));

                            response_view.setText(outputAssistant);
                            response_view.setVisibility(View.VISIBLE);

                            System.out.println("nice date: "+outputAssistant);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context,"Espere...", Toast.LENGTH_LONG).show();
                    }
                });
    }

}