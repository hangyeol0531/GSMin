package com.example.gsmin.Main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsmin.R;

import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;
import com.github.irshulx.models.EditorContent;
import com.github.irshulx.models.EditorTextStyle;
import java.io.IOException;

public class WriteActivity extends AppCompatActivity {
    private ImageButton back, search, menu;
    private ImageView gsmin;
    private LinearLayout wLayout;
    private TextView channelText;
    private Editor editor;
    private Bitmap bitmap;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_write);

        editor = findViewById(R.id.writeEdit);
        setUpEditor();

        gsmin = findViewById(R.id.gsmin);
        gsmin.setVisibility(View.GONE);

        back = findViewById(R.id.drawer_btn);
        back.setVisibility(View.VISIBLE);
        back.setBackgroundResource(R.drawable.arrow_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        wLayout = findViewById(R.id.writeLayout);
        wLayout.setVisibility(View.VISIBLE);

        channelText = findViewById(R.id.channelText);
        channelText.setText(BoardActivity.channel);

        search = findViewById(R.id.searchBtn);
        search.setVisibility(View.INVISIBLE);

        menu = findViewById(R.id.menuBtn);
        menu.setBackgroundResource(R.drawable.mdi_send);


        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
    }

    private void setUpEditor() {
        editor.setFontFace(R.string.fontFamily__sans_serif);
        {
            findViewById(R.id.action_h1).setVisibility(View.GONE);
            findViewById(R.id.action_h2).setVisibility(View.GONE);
            findViewById(R.id.action_h3).setVisibility(View.GONE);
            findViewById(R.id.action_bold).setVisibility(View.GONE);
            findViewById(R.id.action_Italic).setVisibility(View.GONE);
            findViewById(R.id.action_indent).setVisibility(View.GONE);
            findViewById(R.id.action_blockquote).setVisibility(View.GONE);
            findViewById(R.id.action_outdent).setVisibility(View.GONE);
            findViewById(R.id.action_bulleted).setVisibility(View.GONE);
            findViewById(R.id.action_unordered_numbered).setVisibility(View.GONE);
            findViewById(R.id.action_hr).setVisibility(View.GONE);
            findViewById(R.id.action_color).setVisibility(View.GONE);
            findViewById(R.id.action_erase).setVisibility(View.GONE);
            findViewById(R.id.action_insert_link).setVisibility(View.GONE);
        }

//        findViewById(R.id.action_h1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.updateTextStyle(EditorTextStyle.H1);
//            }
//        });
//
//        findViewById(R.id.action_h2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.updateTextStyle(EditorTextStyle.H2);
//            }
//        });
//
//        findViewById(R.id.action_h3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.updateTextStyle(EditorTextStyle.H3);
//            }
//        });
//
//        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.updateTextStyle(EditorTextStyle.BOLD);
//            }
//        });
//
//        findViewById(R.id.action_Italic).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.updateTextStyle(EditorTextStyle.ITALIC);
//            }
//        });
//

//        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.updateTextStyle(EditorTextStyle.INDENT);
//            }
//        });
//
//        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.updateTextStyle(EditorTextStyle.BLOCKQUOTE);
//            }
//        });
//
//        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.updateTextStyle(EditorTextStyle.OUTDENT);
//            }
//        });
//
//        findViewById(R.id.action_bulleted).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.insertList(false);
//            }
//        });
//
//        findViewById(R.id.action_unordered_numbered).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.insertList(true);
//            }
//        });
//
//        findViewById(R.id.action_hr).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.insertDivider();
//            }
//        });
//
//        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.insertLink();
//            }
//        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.openImagePicker();
            }
        });
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
        startActivity(intent);
        WriteActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == editor.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Log.d("bitmap", String.valueOf(bitmap));
                editor.insertImage(bitmap);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

}