package com.example.bpgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bpgapp.ui.XyValues;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphTest extends AppCompatActivity {
    private static final String TAG = "GraphTest";

    //Add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> xySeries;

    PointsGraphSeries<DataPoint> onClickSeries;

    //Create GraphView object
    GraphView mScatterPlot;

    //make xyValueArray global
    ArrayList<XyValues> xyValueArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_test);

        //Declare GraphView object
        mScatterPlot = (GraphView) findViewById(R.id.scatterPlot);

        xySeries = new PointsGraphSeries<>();

        //!!!!Added code start
        //List<TableModelBP> table_list;
       // List<BPData> dataList;
        BPData data = new BPData();
        //Set text on main data
        String systolicStr = data.getSystolicText();
        double systolicVal = Integer.parseInt(systolicStr);

       // BPData data = dataList.get(position);
        BPRoomDB bpDatabase;
        //Initialize database
        bpDatabase = BPRoomDB.getInstance(getApplicationContext());

        /*
        //Set the text for each item in the table
        if(table_list != null && table_list.size() > 0)  {
            TableModelBP model = table_list.get(position);
            xyValueArray.add(systolicVal);

        }
        else  {
            return;
        }
        */

        //!!!!Added code end

        //generate two lists of random values, one for x and one for y.
        xyValueArray = new ArrayList<>();
        double start = 0;
        double end = 150;
        for(int i = 0; i<40; i++){
            double randomX = new Random().nextDouble();
            double randomY = new Random().nextDouble();
            double x = start + (randomX * (end - start));
            double y = start + (randomY * (end - start));
            xyValueArray.add(new XyValues(i,systolicVal));
        }


        //sort it in ASC order
        xyValueArray = sortArray(xyValueArray);
        //add the data to the series
        for(int i = 0;i <xyValueArray.size(); i++){
            double x = xyValueArray.get(i).getX();
            double y = xyValueArray.get(i).getY();
            xySeries.appendData(new DataPoint(x,y),true, 1000);
        }

        createScatterPlot();
    }

    private void createScatterPlot() {
        Log.d(TAG, "createScatterPlot: Creating scatter plot.");

        xySeries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Log.d(TAG, "onTap: You clicked on: (" + dataPoint.getX() +
                        "," + dataPoint.getY() + ")");
                //declare new series
                onClickSeries = new PointsGraphSeries<>();

                onClickSeries.appendData(new DataPoint(dataPoint.getX(), dataPoint.getY()), true, 100);

                onClickSeries.setShape(PointsGraphSeries.Shape.RECTANGLE);

                onClickSeries.setColor(Color.RED);

                onClickSeries.setSize(10f);

                mScatterPlot.removeAllSeries();

                mScatterPlot.addSeries(onClickSeries);

                mScatterPlot.setTitle("Blood Pressure");

                toastMessage("x = " + dataPoint.getX() + "\n" +
                        "y = " + dataPoint.getY());

                createScatterPlot();
            }
        });

        //set some properties
        xySeries.setShape(PointsGraphSeries.Shape.POINT);
        xySeries.setColor(Color.BLUE);
        xySeries.setSize(10f);

        //set Scrollable and Scaleable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(200);
        mScatterPlot.getViewport().setMinY(0);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(200);
        mScatterPlot.getViewport().setMinX(0);

        mScatterPlot.addSeries(xySeries);
    }

    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    private ArrayList<XyValues> sortArray(ArrayList<XyValues> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size()-1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");

        while(true){
            m--;
            if(m <= 0){
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try{
                //print out the y entries so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m-1).getY();
                double tempX = array.get(m-1).getX();
                if(tempX > array.get(m).getX() ){
                    array.get(m-1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m-1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                }
                else if(tempY == array.get(m).getY()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }

                else if(array.get(m).getX() > array.get(m-1).getX()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if(count == factor ){
                    break;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }

        /**
         * customizable toast
                 * @param message
                 */
                private void toastMessage(String message){
                    Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
                }
}