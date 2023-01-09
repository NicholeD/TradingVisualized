//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BurstBubbles : MonoBehaviour
{
    public GameObject bubbles;
    void Start()
    {
        StartCoroutine(OneSecond(bubbles));
    }

    IEnumerator OneSecond(GameObject clone)
    {
        yield return new WaitForSeconds(5f);
        Destroy(clone);

    }
}
