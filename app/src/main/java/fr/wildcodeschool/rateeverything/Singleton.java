package fr.wildcodeschool.rateeverything;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by wilder on 25/04/18.
 */

public class Singleton {
    public static Singleton sIntance = null;

    public Boolean mTestFollow;
    public ArrayList<MainPhotoModel> mListPrincipal = new ArrayList<>();

    public ArrayList<MainPhotoModel> getmListPrincipal() {
        return mListPrincipal;
    }

    public LoadListener mListener = null;

    public Singleton() {
        loadList();
    }

    public static Singleton getsIntance() {
        if (sIntance == null){
            sIntance = new Singleton();
        }
        return sIntance;
    }

    public void loadList() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();
        DatabaseReference listRef = database.getReference("Users/");
        listRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    final String testFollowers = userSnapshot.getKey();
                    mTestFollow = false;
                    if (dataSnapshot.child(userId).child("Followers").child(testFollowers).exists()){
                        mTestFollow = (Boolean) dataSnapshot.child(userId).child("Followers").child(testFollowers).getValue();
                        if(mTestFollow){
                            for (DataSnapshot photoSnapshot : userSnapshot.child("Photo").getChildren()) {
                                MainPhotoModel mObjetPhoto = photoSnapshot.getValue(MainPhotoModel.class);
                                mListPrincipal.add(mObjetPhoto);
                            }
                            if (mListener != null) {
                                mListener.onListUpdate(mListPrincipal);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void setListener(LoadListener listener) {
        this.mListener = listener;
    }

    public interface LoadListener{
        void onListUpdate(ArrayList<MainPhotoModel> photo);
    }
}
