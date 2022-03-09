package com.technothinksup.consciouskitchen.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.ContactListAdapter;
import com.technothinksup.consciouskitchen.extra.Common;

import java.util.ArrayList;
import java.util.List;


public class ContactListFragment extends Fragment {

    private View view;

    private List<ContactResponse> contactResponseList = new ArrayList<>();

    private RecyclerView rv_contact_list;

    private ContactListAdapter contactListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        init();
        requestPermission();


        imageBackListener();


        return view;
    }

    private void imageBackListener() {
        ImageView imageView_Back = view.findViewById(R.id.imageView_Back);

        imageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }

    private void init() {
        rv_contact_list = view.findViewById(R.id.rv_contact_list);
    }

    private void setContactAdapter() {


        contactListAdapter = new ContactListAdapter(getActivity(), contactResponseList);
        rv_contact_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_contact_list.setAdapter(contactListAdapter);
        contactListAdapter.notifyDataSetChanged();
        rv_contact_list.setHasFixedSize(true);
    }

    private void getContactList() {
        Common.showProgressBar(getActivity());

        String[] projection = new String[]{ContactsContract.Contacts._ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_URI};
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        Log.e("count", "" + phones.getCount());

        String lastPhoneName = " ";

        if (phones.getCount() > 0) {
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim().replaceAll("\\s+", "");
                if (phoneNumber.length() > 10) {
                    phoneNumber = phoneNumber.substring(phoneNumber.length() - 10);
                } else {
                    phoneNumber = phoneNumber;
                }

                String contactId = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
                String photoUri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                if (!name.equalsIgnoreCase(lastPhoneName)) {
                    lastPhoneName = name;
                    ContactResponse contactResponse = new ContactResponse();
                    contactResponse.setCustomerName(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                    contactResponse.setCustomerNumber(phoneNumber);
                    contactResponseList.add(contactResponse);
                    Log.d("getContactsList", phoneNumber);
                }
            }
        }
        phones.close();



        setContactAdapter();

        Common.removeProgressBar(getActivity());

       /* contactAdapter = new ContactAdapter(getActivity(), contactResponseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
        contactAdapter.notifyItemInserted(contactResponseList.size() - 1);
        recyclerView.setHasFixedSize(true);*/


    }

    private void requestPermission() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_CONTACTS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            getContactList();
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}