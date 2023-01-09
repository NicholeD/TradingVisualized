//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;

[System.Serializable]
public class Fish
{
    public int id;
    public string name;
    public float size;
    public float quantity;
    public float price;
    public string status;

    public Fish() { }
    public Fish(string name, float size, float quantity, float price, string status)
    {
        this.id = 0;
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }


}
