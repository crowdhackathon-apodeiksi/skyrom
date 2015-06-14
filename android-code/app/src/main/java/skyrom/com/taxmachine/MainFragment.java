package skyrom.com.taxmachine;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import skyrom.com.taxmachine.requests.UploadCcn;
import skyrom.com.taxmachine.utils.Utils;


public class MainFragment extends Fragment implements UploadCcn.Callback {
    static final int CAMERA_REQUEST = 1888;
    static final int QR_CODE_REQUEST = 1;
    ImageButton button;
    SQLite sqLite;
    Uri imageUri;
    TextView rank;
    TextView totalDraws;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        button = (ImageButton) view.findViewById(R.id.add_receipt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle(R.string.has_qr_code);
                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        startActivityForResult(new Intent(getActivity(), QrCodeActivity.class),
                                QR_CODE_REQUEST);
                    }
                });
                alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        captureImage();
                    }
                });
                alertDialog.show();
            }
        });
        sqLite = SQLite.getInstance(getActivity());
        rank = (TextView) view.findViewById(R.id.rank);
        totalDraws = (TextView) view.findViewById(R.id.total_draws);
        totalDraws.setText(String.valueOf(DummyProvider.getDraws(getActivity()).size()));
        return view;
    }

    private void captureImage() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getActivity().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    Intent intent = new Intent(getActivity(), CropActivity.class);
                    intent.putExtra(Utils.IMAGE_URI, imageUri.toString());
                    startActivity(intent);
                    break;
                case QR_CODE_REQUEST:
                    String ccn = data.getStringExtra(Utils.RESULT_EXTRA_STR);
                    new UploadCcn(getActivity(), SessionManager.getInstance(getActivity()).getAuthToken(),
                            ccn, this).perform();
            }
        }
    }

    @Override
    public void ccnUploaded(UploadCcn request, Receipt receipt) {
        if (request.hasError()) {
            Toast.makeText(getActivity(), "CCn not uploaded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "CCn uploaded", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), AlternateShowReceipt.class);
            intent.putExtra(Utils.RECEIPT, receipt);
            startActivity(intent);
        }
    }
}
