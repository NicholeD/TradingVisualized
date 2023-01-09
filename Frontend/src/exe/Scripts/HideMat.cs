//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HideMat : MonoBehaviour
{
    Renderer renderer;
    void Start()
    {
        renderer = GetComponent<Renderer>();
        renderer.enabled = false;
    }



}
