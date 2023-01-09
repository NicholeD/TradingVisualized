//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MoveCamera : MonoBehaviour
{
    public GameObject innerWall;
    bool forwardColliding = false;
    bool backColliding = false;



    private void OnTriggerEnter(Collider other)
    {
        //colliding = true;
        //Debug.Log("Hit Detected");
        if (other.transform.tag == "wall1")
        {
            Debug.Log("I hit the Inner Wall");
            forwardColliding = true;
        }
        else if (other.transform.tag == "wall2") {
            Debug.Log("I hit the Outer Wall");
            backColliding = true;

        }
    }
    private void OnCollisionExit(Collision collision)
    {
        forwardColliding = false;
        backColliding = false;
    }


    void Update()
    {

        if (Input.GetKey(KeyCode.UpArrow) && forwardColliding == false) {
            this.transform.Translate(Vector3.forward * .2f);
            backColliding = false;
        }
        else if(Input.GetKey(KeyCode.UpArrow) && forwardColliding == true)
        {
            this.transform.Translate(Vector3.forward * 0f);
        }

        if (Input.GetKey(KeyCode.DownArrow) && backColliding == false)
        {
            this.transform.Translate(Vector3.back * .2f);
            forwardColliding = false;
        }
        else if (Input.GetKey(KeyCode.DownArrow) && backColliding == true) {
            this.transform.Translate(Vector3.back * 0f);
        }

    }
}
