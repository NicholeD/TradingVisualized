//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Fish_Controller : MonoBehaviour
{
    public Material[] material;
    Renderer rend;
    public int number;
    public bool usedNumber;


    void Start()
    {
        usedNumber = true;
        while (usedNumber == true) {
            rando();
            usedNumber = check();
        }

        rend = GetComponent<Renderer>();
        rend.enabled = true;
        rend.sharedMaterial = material[number];

    }
    public void rando() {
        number = Random.Range(1, 10);
    }
    public bool check() {
        for (int i = 0; i < Hub.usedNumbers.Count; i++)
        {
            if (number == Hub.usedNumbers[i])
            {
                usedNumber = true;
                return true;
            }
        }
        Hub.usedNumbers.Add(number);
        return false;
        
    }

}
