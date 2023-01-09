//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class DemoMode : MonoBehaviour
{
    public Toggle selectedToggle;
    void Start()
    {
        Hub.demoMode = true;
        selectedToggle.onValueChanged.AddListener(delegate {
            ToggleOccured(selectedToggle);
        });

    }

    public void ToggleOccured(Toggle tgValue) {
        Debug.Log("Current State: "+ tgValue.isOn);
        Hub.demoMode = tgValue.isOn;
    
    }
    void Update()
    {
        //Debug.Log(Hub.demoMode);
    }
}
