//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class rotateCamera : MonoBehaviour
{
    // Start is called before the first frame update  
    void Start()
    {

    }

    // Update is called once per frame  
    void Update()
    {


        if (Input.GetKey(KeyCode.LeftArrow))
        {
            this.transform.Rotate(new Vector3(0, 200, 0) * Time.deltaTime);
        }

        if (Input.GetKey(KeyCode.RightArrow))
        {
            this.transform.Rotate(new Vector3(0, -200, 0) * Time.deltaTime);
        }

    }
}
