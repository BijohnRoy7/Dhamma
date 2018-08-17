package invenz.example.bijohn.dhamma.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import invenz.example.bijohn.dhamma.adapters.QuotesCustomAdapter;
import invenz.example.bijohn.dhamma.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ListView quotesListView;
    private ViewFlipper myViewFlipper;
    private int[] images ={R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.i};
    private QuotesCustomAdapter customAdapter;
    private String[] quotes;
    private String qouteDetailsText, qouteHeadText;
    private Dialog dialogQuotes;
    private TextView tvQuoteHeading, tvQuoteDetails, tvDialogCross;

    //private String[] quotes = {"hgj", "hgj", "tf"};


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        myViewFlipper = view.findViewById(R.id.idFlipper_mainFrag);
        quotesListView = view.findViewById(R.id.idListQoutes_mainFrag);
        quotes = getResources().getStringArray(R.array.quotes);

        /*######## SLIDER #########*/

        for (int img: images){
            addFlipperImage(img);
        }

        /*############### ListView #####################*/
        customAdapter = new QuotesCustomAdapter(getContext(), quotes);
        quotesListView.setAdapter(customAdapter);

        quotesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*################ Creating dialog ###################*/
                dialogQuotes = new Dialog(getContext());
                dialogQuotes.setContentView(R.layout.quotes_dialog);
                Window window = dialogQuotes.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialogQuotes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                tvQuoteHeading = dialogQuotes.findViewById(R.id.idQuoteDetailsHeading_quotesDialog);
                tvQuoteDetails = dialogQuotes.findViewById(R.id.idQuoteDetails_quotesDialog);
                String str = quotes[position];
                //tvQuoteHeading.setText(str);



                switch (position){
                    case 0:
                        qouteDetailsText = getContext().getResources().getString(R.string.zero);
                        qouteHeadText = getContext().getResources().getString(R.string.q0);
                        break;
                    case 1:
                        qouteDetailsText = getContext().getResources().getString(R.string.one);
                        qouteHeadText = getContext().getResources().getString(R.string.q1);
                        break;
                    case 2:
                        qouteDetailsText = getContext().getResources().getString(R.string.two);
                        qouteHeadText = getContext().getResources().getString(R.string.q2);
                        break;
                    case 3:
                        qouteDetailsText = getContext().getResources().getString(R.string.three);
                        qouteHeadText = getContext().getResources().getString(R.string.q3);
                        break;
                    case 4:
                        qouteDetailsText = getContext().getResources().getString(R.string.four);
                        qouteHeadText = getContext().getResources().getString(R.string.q4);
                        break;
                    case 5:
                        qouteDetailsText = getContext().getResources().getString(R.string.five);
                        qouteHeadText = getContext().getResources().getString(R.string.q5);
                        break;
                    case 6:
                        qouteDetailsText = getContext().getResources().getString(R.string.six);
                        qouteHeadText = getContext().getResources().getString(R.string.q6);
                        break;
                    case 7:
                        qouteDetailsText = getContext().getResources().getString(R.string.seven);
                        qouteHeadText = getContext().getResources().getString(R.string.q7);
                        break;


                }

                tvQuoteHeading.setText(qouteHeadText);
                tvQuoteDetails.setText(qouteDetailsText);

                tvDialogCross = dialogQuotes.findViewById(R.id.idCross_squadDialog);
                tvDialogCross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogQuotes.dismiss();
                    }
                });

                dialogQuotes.show();

            }
        });


        return  view;
    }


    /*####################### SETTING IMAGES FOR SLIDING IMAGE ###################################*/
    public void addFlipperImage(int image){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        myViewFlipper.addView(imageView);
        myViewFlipper.setFlipInterval(3000);
        myViewFlipper.setAutoStart(true);

        //animation
        myViewFlipper.setInAnimation(getActivity(), android. R.anim.slide_in_left);
        myViewFlipper.setOutAnimation(getActivity(), android. R.anim.slide_out_right);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////


}




