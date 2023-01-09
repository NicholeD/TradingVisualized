//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;
using UnityEngine.UI;

[System.Serializable]
public class SeedData : MonoBehaviour
{

    public List<Fish> school = new List<Fish>();
    public GameObject bubleExpode;
    //public Button addFourFish;
    public Dropdown fishOptions;
    public Dropdown fishOption;
    public Text fishText;
    public Text text;
    public int index;

    private void Start()
    {
        //addFourFish.onClick.AddListener(addFour);
        fishOptions.onValueChanged.AddListener(fishActionMenu);

        for (int i = 0; i < Hub.school.Count; i++) {
            if(Hub.school[i].id.ToString() != this.transform.parent.tag && !Hub.school[i].status.Equals("Deceased"))
                fishOptions.options.Add(new Dropdown.OptionData() { text = Hub.school[i].name });
        }
        //fishOptions.value = 1;
        //fishOptions.value = 0;
    }
    IEnumerator waitFive(GameObject clone) {
        yield return new WaitForSeconds(5);
        Destroy(clone);
    }
    private void fishActionMenu(int tag) {
        Debug.Log("###"+tag);
        GameObject[] destroyObject;
        destroyObject = GameObject.FindGameObjectsWithTag((tag).ToString());
        foreach (GameObject oneObject in destroyObject)
        {
            var clone = Instantiate(bubleExpode, new Vector3(oneObject.transform.position.x,oneObject.transform.position.y+.5f,oneObject.transform.position.z), Quaternion.identity);
            Destroy(oneObject);
            Hub.fish -= 1;
            Hub.cash += Hub.school[tag - 1].price * Hub.school[tag - 1].quantity;
            Hub.school[tag-1].status = "Deceased";
            Hub.setTotalValue();
            Debug.Log(" Name: " + Hub.school[tag-1].name + " Status: " + Hub.school[tag-1].status);
            StartCoroutine(waitFive(clone));

        }
    }
    void addFourFish()
    {
        
        //Instantiate Fish Classes
        Fish one = new Fish("One", 10, 100, 30, "Unknown");
        //Add to a List<Fish> school (school of fish)
        school.Add(one);
        Fish two = new Fish("Two", 11f, 88f, 34.50f, "Unknown");
        school.Add(two);
        Fish three = new Fish("Three", 13f, 14f, 111.25f, "Unknown");
        school.Add(three);
        Fish four = new Fish("Four", 15f, 56f, 41.50f, "Unknown");
        school.Add(four);

        //Build a bunch of json's
        string json = JsonUtility.ToJson(one);
        string json2 = JsonUtility.ToJson(two);
        string json3 = JsonUtility.ToJson(three);
        string json4 = JsonUtility.ToJson(four);
        //Put them all together
        //In this case four fish
        string full = json + json2 + json3 + json4;

        //Write a external file
        File.WriteAllText(Application.dataPath + "/data.txt", full);
        Debug.Log("Full: "+full);
        //Fish loadedFish = JsonUtility.FromJson<Fish>(json);
        //Debug.Log("**QTY**"+ loadedFish.quantity);
    }


}
