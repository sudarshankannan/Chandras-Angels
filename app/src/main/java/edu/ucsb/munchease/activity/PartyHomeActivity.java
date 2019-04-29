package edu.ucsb.munchease.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.ucsb.munchease.R;
import edu.ucsb.munchease.data.Party;
import edu.ucsb.munchease.data.Restaurant;
import edu.ucsb.munchease.view.RestaurantAdapter;

public class PartyHomeActivity extends AppCompatActivity {

    //The party
    private Party party;

    //Visual components of the app

    private RecyclerView recyclerView_restaurantList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_home);

        party = new Party();
        party.addRestaurant(new Restaurant("Restaurant 1", "5", 25, "$$", "1234 The Street"));
        //party.addRestaurant(new Restaurant("Restaurant 2", "3", 50, "$$$$", "5678 An Avenue"));

        setUpFirebase();

        populateDatabase();

        //updateParty();

        setUpRestaurantList();

        //------------------------------------------------------------------
        //LIST CONFIGURATION
        //------------------------------------------------------------------

        /*DocumentReference docRef = db.collection("parties").document("123456");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot != null) {
                        party = documentSnapshot.toObject(Party.class);

                        Toast.makeText(getApplicationContext(), party.getRestaurants().size() + "", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/
    }

    private void setUpRestaurantList() {
        recyclerView_restaurantList = findViewById(R.id.recyclerView_restaurantList);
        recyclerView_restaurantList.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView_restaurantList.setLayoutManager(layoutManager);

        //Specify an adapter
        mAdapter = new RestaurantAdapter(party.getRestaurants());
        recyclerView_restaurantList.setAdapter(mAdapter);
    }

    private void setUpFirebase() {
        db = FirebaseFirestore.getInstance();
    }

    private void populateDatabase() {
        Party party2 = new Party();
        party2.addRestaurant(new Restaurant("Restaurant 1", "5", 25, "$$", "1234 The Street"));
        party2.addRestaurant(new Restaurant("Restaurant 2", "3", 50, "$$$$", "5678 An Avenue"));

        // Add a new document with a generated ID
        db.collection("parties").document(party2.getPartyID()).set(party2);
    }

    private void updateParty() {
        DocumentReference docRef = db.collection("parties").document("123456");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot != null) {
                        party = documentSnapshot.toObject(Party.class);

                        Toast.makeText(getApplicationContext(), party.getRestaurants().size() + "", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
