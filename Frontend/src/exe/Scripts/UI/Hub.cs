//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;

public static class Hub
{
    public static List<Fish> school = new List<Fish>();
    public static List<Fish> liveFish = new List<Fish>();
    public static List<int> usedNumbers = new List<int>();
    public static float cash;
    public static int fish;
    public static float totalValue;
    public static float big;
    public static float sml;
    public static float half;
    public static float oneQ;
    public static float threeQ;
    public static float one8th;
    public static float three8th;
    public static float five8th;
    public static float seven8th;
    public static bool actionActive;
    public static bool update;
    public static bool demoMode;
    public static string saveString;

    public static void save() {
        if (demoMode == false)
        {
            for (int i = 0; i < school.Capacity; i++)
            {
                string json = JsonUtility.ToJson(school[i]);
                saveString += json;
            }
            File.Delete(Application.dataPath + "/data.txt");
            File.WriteAllText(Application.dataPath + "/data.txt", saveString);
        }
    }
    
    public static void setSize() {
        setRawSize();
        setTotalValue();
        findMinMax();
        split();
        setGameSize();
        fish = liveFish.Count;
    }
    public static void setLiveFishList() {
        for (int i = 0; i < school.Count; i++)
        {
            if (school[i].status != "Deceased")
                liveFish.Add(Hub.school[i]);
        }
    }
    public static float setIndividualGameSive(int pred, int prey) {
        school[pred].size += (school[prey].size)*.5f;
        if (school[pred].size > 20)
            return 20;

        return school[pred].size;

    }
    public static void setGameSize() {
        for (int i = 0; i < school.Count; i++) {
            if (school[i].size <= one8th)
                school[i].size = 5;
            else if (school[i].size > one8th && school[i].size <= oneQ)
                school[i].size = 7;
            else if (school[i].size > oneQ && school[i].size <= three8th)
                school[i].size = 9;
            else if (school[i].size > three8th && school[i].size <= half)
                school[i].size = 11;
            else if (school[i].size > half && school[i].size <= five8th)
                school[i].size = 13;
            else if (school[i].size > five8th && school[i].size <= threeQ)
                school[i].size = 15;
            else if (school[i].size > threeQ && school[i].size <= seven8th)
                school[i].size = 17;
            else if (school[i].size > seven8th)
                school[i].size = 19;
        }
    }

    public static void split() {
        half = (big + sml) / 2;
        oneQ = (sml + half) / 2;
        threeQ = (half+big) / 2;
        one8th = (sml+oneQ) / 2;
        three8th = (oneQ+half) / 2;
        five8th = (half+threeQ) / 2;
        seven8th = (threeQ+big) / 2;

    }
    public static void setRawSize() {

            for (int i = 0; i < school.Count; i++)
            {
                school[i].size = school[i].price * Hub.school[i].quantity;
            //Debug.Log("Size: "+ Hub.school[i].size);
            totalValue += school[i].size;
            }
        
    }
    public static void setTotalValue()
    {
        totalValue = 0;
        for (int i = 0; i < school.Count; i++)
        {
            if (school[i].status != "Deceased")
            {
                totalValue += school[i].price*school[i].quantity;
                //Debug.Log("Deceased: " + school[i].status);
            }
        }

    }

    public static void findMinMax()
    {
        big = 0f;
        for (int i = 0; i < Hub.school.Count; i++)
        {
            if (Hub.school[i].size > big)
                big = Hub.school[i].size;
        }
        sml = big;
        for (int i = 0; i < Hub.school.Count; i++)
        {
            if (Hub.school[i].size < sml)
            {
                sml = Hub.school[i].size;
                //Debug.Log("Building sml: "+sml);
            }
        }
    }


}
