package android.dayuteam.cn.arcgis;

import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.esri.android.map.FeatureLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.renderer.Renderer;
import com.esri.core.renderer.SimpleRenderer;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.Symbol;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    MapView mMapView;
    ArcGISTiledMapServiceLayer tileLayer;
    FeatureLayer featureLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.mapview);
        tileLayer = new ArcGISTiledMapServiceLayer
                ("http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
        // Add tiled layer to MapView
        mMapView.addLayer(tileLayer);

        String shpPath = getSDPath() + "/download/bou2_4p.shp";
        Toast.makeText(this,shpPath,Toast.LENGTH_LONG).show();
        Symbol symbol = new SimpleFillSymbol(Color.BLUE);
        Renderer renderer = new SimpleRenderer(symbol);

        try {
            ShapefileFeatureTable shapefileFeatureTable = new ShapefileFeatureTable(shpPath);
            featureLayer = new FeatureLayer(shapefileFeatureTable);
            Toast.makeText(this,featureLayer.getFeatureTable().getTableName(),Toast.LENGTH_LONG).show();
            featureLayer.setRenderer(renderer);
            mMapView.addLayer(featureLayer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getSDPath() {
        String sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();//获取跟目录
        }
        return sdDir.toString();

    }

}
