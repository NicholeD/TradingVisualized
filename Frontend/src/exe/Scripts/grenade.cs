//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class grenade : MonoBehaviour
{
    public Rigidbody rigidbody;
    public GameObject bubbles;
    public GameObject baBoom;

    private void Awake()
    {
        rigidbody = GetComponent<Rigidbody>();
        rigidbody.useGravity = true;
    }

    private void OnTriggerEnter(Collider collision)
    {
        if (collision.tag == "Boom") {
            GameObject clone = Instantiate(bubbles, new Vector3(this.transform.position.x, this.transform.position.y + .5f, this.transform.position.z), Quaternion.identity);
            GameObject boom = Instantiate(baBoom, new Vector3(this.transform.position.x, this.transform.position.y, this.transform.position.z), Quaternion.identity);
            Destroy(this.gameObject);
        }
    }

}
