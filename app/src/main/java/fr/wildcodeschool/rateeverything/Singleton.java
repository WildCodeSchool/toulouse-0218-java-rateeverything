package fr.wildcodeschool.rateeverything;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
                mListPrincipal.clear();
                Map <Long, MainPhotoModel> sortedPhotoList = new TreeMap<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    final String testFollowers = userSnapshot.getKey();
                    mTestFollow = false;
                    if (testFollowers.equals(userId)){
                        for (DataSnapshot photoSnapshot : userSnapshot.child("Photo").getChildren()) {
                            MainPhotoModel mObjetPhoto = photoSnapshot.getValue(MainPhotoModel.class);
                            sortedPhotoList.put(mObjetPhoto.getTimestamp(), mObjetPhoto);
                        }

                    }
                    if (dataSnapshot.child(userId).child("Followers").child(testFollowers).exists()){
                        mTestFollow = (Boolean) dataSnapshot.child(userId).child("Followers").child(testFollowers).getValue();
                        if(mTestFollow){
                            for (DataSnapshot photoSnapshot : userSnapshot.child("Photo").getChildren()) {
                                MainPhotoModel mObjetPhoto = photoSnapshot.getValue(MainPhotoModel.class);
                                sortedPhotoList.put(mObjetPhoto.getTimestamp(), mObjetPhoto);
                            }
                        }
                    }
                }
                for (Map.Entry<Long, MainPhotoModel> entry : sortedPhotoList.entrySet()){
                    mListPrincipal.add(entry.getValue());
                }

                if (mListener != null) {
                    mListener.onListUpdate(mListPrincipal);
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
