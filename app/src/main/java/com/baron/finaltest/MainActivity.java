package com.baron.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.QRCodeDetector;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageView imageView;
    ImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        imageView=findViewById(R.id.imageView);
        imageView2=findViewById(R.id.imageView2);

        if(!OpenCVLoader.initDebug()){
            Log.d("run","unavailable");
        }else{
            Log.d("run","Normal");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                Bitmap bitmap2 = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
                Mat mat = new Mat();
                Mat mat2 = new Mat();
                Utils.bitmapToMat(bitmap,mat);


                QRCodeDetector qrCodeDetector=new QRCodeDetector();
                String result = qrCodeDetector.detectAndDecode(mat);
                Log.d("MainActivity", "onclick"+ result);


                String[] lines = result.split(" ");
                for(int i = 0; i< lines.length; i++) {
                    String line = lines[i];
                    String pointA = line.split(" ")[0];
                    String xA = line.split(",")[0];
                    String yA = line.split(",")[1];
                    String xB = line.split(",")[0];
                    String yB = line.split(",")[1];
                    Point point1= new Point(Double.valueOf(xA), Double.valueOf(yA));
                    Point point2= new Point(Double.valueOf(xB), Double.valueOf(yB));
                    drawline(mat2,point1,point2);

                }

                Utils.matToBitmap(mat,bitmap);
                Utils.matToBitmap(mat2,bitmap2);


            }
        });







    }
    void drawline(Mat mat2, Point point1, Point point2)  {
        Scalar color = new Scalar(225,0,0,225);
        Imgproc.line(mat2, point1, point2,color, 5);
    }
}
