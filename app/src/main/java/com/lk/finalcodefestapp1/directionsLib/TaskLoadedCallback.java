package com.lk.finalcodefestapp1.directionsLib;

import com.lk.finalcodefestapp1.Model.Mapdistanceobj;
import com.lk.finalcodefestapp1.Model.Maptimeobj;

/**
 * Created by Vishal on 10/20/2018.
 */

public interface TaskLoadedCallback {
    void onTaskDone(Object... values);
    void onTimeTaskDone(Maptimeobj maptimeobj);
    void onDistanceTaskDone(Mapdistanceobj mapdistanceobj);


}
