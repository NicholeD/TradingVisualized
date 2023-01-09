//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;
using UnityEngine.UI;

public class Instantiate : MonoBehaviour
{
    public Transform prefab;
    public Transform prefabTwo;
    public Transform prefabThree;
    public Transform prefabFour;
    public Transform prefabFive;
    public Transform prefabSix;
    public Transform prefabSeven;
    public Transform prefabEight;
    public Transform parent;
    public GameObject parentObj;
    public GameObject leCamera;
    public GameObject innerWall;
    public Toggle demoToggle;
    public Text demoText;
    public Text totalValue;
    public Text totalFish;
    public Text totalCash;
    public float one;
    public float two;
    public float three;
    public float four;
    public float five;
    public float six;
    public float seven;
    public float eight;
    public float nine;
    public float ten;
    public float eleven;
    public float twelve;
    public float thirteen;
    public float fourteen;
    public float fifteen;
    public float sixteen;
    public float seventeen;
    public float eighteen;
    public float nineteen;
    public float twenty;

    //public List<Fish> school = new List<Fish>();

    public void Update()
    {
        totalValue.text = Hub.totalValue.ToString("C");
        totalFish.text = Hub.fish.ToString();
        totalCash.text = Hub.cash.ToString("C");
    }
    public void Awake()
    {
        Hub.fish = 0;
        Spawn();
    }
    public void Start()
    {
       
    }
        public void ToggleValueChanged(Toggle change)
    {
        demoText.text = "Toggle is : " + demoToggle.isOn;
    }
    private void Spawn()
    {

        try
        {
            string str = System.IO.File.ReadAllText(Application.dataPath + "/data.txt");
            //Debug.Log("**" + str);

            string[] array = str.Split('}');

            int numFish = array.Length - 1;
            //Debug.Log("Total Fish: " + numFish);

            for (int i = 0; i < numFish; i++)
            {
                Fish fish = JsonUtility.FromJson<Fish>(array[i] + "}");
                fish.id = i + 1;
                Hub.school.Add(fish);
            }
            Hub.setLiveFishList();


            Hub.setSize();

            for (int i = 0; i < Hub.liveFish.Count; i++)
            {
                if (Hub.liveFish[i].id == 1)
                    one = Hub.school[i].size;
                else if (Hub.liveFish[i].id == 2)
                    two = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 3)
                    three = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 4)
                    four = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 5)
                    five = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 6)
                    six = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 7)
                    seven = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 8)
                    eight = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 9)
                    nine = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 10)
                    ten = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 11)
                    eleven = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 12)
                    twelve = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 13)
                    thirteen = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 14)
                    fourteen = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 15)
                    fifteen = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 16)
                    sixteen = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 17)
                    seventeen = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 18)
                    eighteen = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 19)
                    nineteen = Hub.liveFish[i].size;
                else if (Hub.liveFish[i].id == 20)
                    twenty = Hub.liveFish[i].size;
            }

            //Debug.Log("QTY: " + school[2].name);
            if (Hub.liveFish.Count <= 4)
            {
                fourFish();
            }
            else if (Hub.liveFish.Count <= 8)
            {
                eightFish();
            }
            else if (Hub.liveFish.Count <= 16)
            {
                sixteenFish();
            }
            else
                webGL();
        }
        catch
        {
            webGL();
            Debug.Log("I loaded the default");
        }


        void fourFish() {
            leCamera.transform.position = new Vector3(0.0f, -0.702f, -4.09f);


            prefab = Instantiate(prefab, new Vector3(0.68F, -2.67f, -3.13f), Quaternion.identity);
            prefab.Rotate(0.0f, -90f, 0.0f);
            prefab.transform.parent = parent;
            prefab.gameObject.transform.localScale = new Vector3(one, one, one);
            prefab.gameObject.tag = "1";

            prefabTwo = Instantiate(prefab, new Vector3(-2.7F, -2.67f, -0.38f), Quaternion.identity);
            prefabTwo.Rotate(0.0f, 0.0f, 0.0f);
            prefabTwo.transform.parent = parent;
            prefabTwo.gameObject.transform.localScale = new Vector3(two,two,two);
            prefabTwo.gameObject.tag = "2";

            prefabThree = Instantiate(prefab, new Vector3(0.14F, -2.67f, 2.93f), Quaternion.identity);
            prefabThree.Rotate(0.0f, 90f, 0.0f);
            prefabThree.transform.parent = parent;
            prefabThree.gameObject.transform.localScale = new Vector3(three,three,three);
            prefabThree.gameObject.tag = "3";

            prefabFour = Instantiate(prefab, new Vector3(3.48F, -2.67f, 0.12f), Quaternion.identity);
            prefabFour.Rotate(0.0f, 180f, 0.0f);
            prefabFour.transform.parent = parent;
            prefabFour.gameObject.transform.localScale = new Vector3(four,four,four);
            prefabFour.gameObject.tag = "4";
        }
        void eightFish()
        {
            leCamera.transform.position = new Vector3(0.0f, -0.702f, -6.04f);
            innerWall.transform.position = new Vector3(0.0f, 0.0f, 0.0f);
            innerWall.gameObject.transform.localScale = new Vector3(9.193535f, 9.193535f, 9.193535f);

            if (Hub.school.Count >= 1)
            {
                prefab = Instantiate(prefab, new Vector3(4.164681F, -2.67f, -2.884134f), Quaternion.identity);
                prefab.Rotate(0.0f, -137.582f, 0.0f);
                prefab.transform.parent = parent;
                prefab.gameObject.transform.localScale = new Vector3(one, one, one);
                prefab.gameObject.tag = "1";
            }

            if (Hub.school.Count >= 2)
            {
                prefabTwo = Instantiate(prefab, new Vector3(-3.404857F, -2.67f, 3.300628f), Quaternion.identity);
                prefabTwo.Rotate(0.0f, 42.418f, 0.0f);
                prefabTwo.transform.parent = parent;
                prefabTwo.gameObject.transform.localScale = new Vector3(two, two, two);
                prefabTwo.gameObject.tag = "2";
            }

            if (Hub.school.Count >= 3)
            {
                prefabThree = Instantiate(prefab, new Vector3(3.45944F, -2.67f, 3.964124f), Quaternion.identity);
                prefabThree.Rotate(0.0f, 132.418f, 0.0f);
                prefabThree.transform.parent = parent;
                prefabThree.gameObject.transform.localScale = new Vector3(three, three, three);
                prefabThree.gameObject.tag = "3";
            }

            if (Hub.school.Count >= 4)
            {
                prefabFour = Instantiate(prefab, new Vector3(-2.640182F, -2.67f, -3.45293f), Quaternion.identity);
                prefabFour.Rotate(0.0f, -47.582f, 0.0f);
                prefabFour.transform.parent = parent;
                prefabFour.gameObject.transform.localScale = new Vector3(four, four, four);
                prefabFour.gameObject.tag = "4";
            }

            if (Hub.school.Count >= 5)
            {
                prefabFive = Instantiate(prefab, new Vector3(0.1160853F, -2.67f, 5.058713f), Quaternion.identity);
                prefabFive.Rotate(0.0f, 90f, 0.0f);
                prefabFive.transform.parent = parent;
                prefabFive.gameObject.transform.localScale = new Vector3(five, five, five);
                prefabFive.gameObject.tag = "5";
            }

            if (Hub.school.Count >= 6)
            {
                prefabSix = Instantiate(prefab, new Vector3(0.6560853F, -2.67f, -4.631287f), Quaternion.identity);
                prefabSix.Rotate(0.0f, -90f, 0.0f);
                prefabSix.transform.parent = parent;
                prefabSix.gameObject.transform.localScale = new Vector3(six, six, six);
                prefabSix.gameObject.tag = "6";
            }

            if (Hub.school.Count >= 7)
            {
                prefabSeven = Instantiate(prefab, new Vector3(5.206086F, -2.67f, 0.498713f), Quaternion.identity);
                prefabSeven.Rotate(0.0f, 180f, 0.0f);
                prefabSeven.transform.parent = parent;
                prefabSeven.gameObject.transform.localScale = new Vector3(seven, seven, seven);
                prefabSeven.gameObject.tag = "7";
            }

            if (Hub.school.Count == 8)
            {
                prefabEight = Instantiate(prefab, new Vector3(-4.373915F, -2.67f, -0.001286864f), Quaternion.identity);
                prefabEight.Rotate(0.0f, 0.0f, 0.0f);
                prefabEight.transform.parent = parent;
                prefabEight.gameObject.transform.localScale = new Vector3(eight, eight, eight);
                prefabEight.gameObject.tag = "8";
            }


        }
        void sixteenFish()
        {
            leCamera.transform.position = new Vector3(0.0f, -0.702f, -4.09f);
            innerWall.transform.position = new Vector3(0.0f, 0.0f, 0.0f);
            innerWall.gameObject.transform.localScale = new Vector3(19.40881f, 19.40881f, 19.40881f);

            if (Hub.school.Count >= 1)
            {
                prefab = Instantiate(prefab, new Vector3(0.29F, -2.67f, -10.35f), Quaternion.identity);
                prefab.Rotate(0.0f, -89.459f, 0.0f);
                prefab.transform.parent = parent;
                prefab.gameObject.transform.localScale = new Vector3(one, one, one);
                prefab.gameObject.tag = "1";
            }
            //
            if (Hub.school.Count >= 2)
            {
                prefabTwo = Instantiate(prefab, new Vector3(-0.06F, -2.67f, 9.86f), Quaternion.identity);
                prefabTwo.Rotate(0.0f, 90f, 0.0f);
                prefabTwo.transform.parent = parent;
                prefabTwo.gameObject.transform.localScale = new Vector3(two, two, two);
                prefabTwo.gameObject.tag = "2";
            }
            //
            if (Hub.school.Count >= 3)
            {
                prefabThree = Instantiate(prefab, new Vector3(-10.28F, -2.67f, -0.22f), Quaternion.identity);
                prefabThree.Rotate(0.0f, 0.0f, 0.0f);
                prefabThree.transform.parent = parent;
                prefabThree.gameObject.transform.localScale = new Vector3(three, three, three);
                prefabThree.gameObject.tag = "3";
            }
            //
            if (Hub.school.Count >= 4)
            {
                prefabFour = Instantiate(prefab, new Vector3(9.87F, -2.67f, -0.29f), Quaternion.identity);
                prefabFour.Rotate(0.0f, 180.0f, 0.0f);
                prefabFour.transform.parent = parent;
                prefabFour.gameObject.transform.localScale = new Vector3(four, four, four);
                prefabFour.gameObject.tag = "4";
            }
            //
            if (Hub.school.Count >= 5)
            {
                prefabFive = Instantiate(prefab, new Vector3(-7.49F, -2.67f, 6.62f), Quaternion.identity);
                prefabFive.Rotate(0.0f, 50.339f, 0.0f);
                prefabFive.transform.parent = parent;
                prefabFive.gameObject.transform.localScale = new Vector3(five, five, five);
                prefabFive.gameObject.tag = "5";
            }
            //
            if (Hub.school.Count >= 6)
            {
                prefabSix = Instantiate(prefab, new Vector3(7.08F, -2.67f, -7.15f), Quaternion.identity);
                prefabSix.Rotate(0.0f, 228.778f, 0.0f);
                prefabSix.transform.parent = parent;
                prefabSix.gameObject.transform.localScale = new Vector3(six, six, six);
                prefabSix.gameObject.tag = "6";
            }
            //
            if (Hub.school.Count >= 7)
            {
                prefabSeven = Instantiate(prefab, new Vector3(-7.07F, -2.67f, -7.4f), Quaternion.identity);
                prefabSeven.Rotate(0.0f, -41.877f, 0.0f);
                prefabSeven.transform.parent = parent;
                prefabSeven.gameObject.transform.localScale = new Vector3(seven, seven, seven);
                prefabSeven.gameObject.tag = "7";
            }
            //
            if (Hub.school.Count >= 8)
            {
                prefabEight = Instantiate(prefab, new Vector3(7.45F, -2.67f, 6.76f), Quaternion.identity);
                prefabEight.Rotate(0.0f, 138.123f, 0.0f);
                prefabEight.transform.parent = parent;
                prefabEight.gameObject.transform.localScale = new Vector3(eight, eight, eight);
                prefabEight.gameObject.tag = "8";
            }

            if (Hub.school.Count >= 9)
            {
                prefab = Instantiate(prefab, new Vector3(-9.334F, -2.67f, -4.174f), Quaternion.identity);
                prefab.Rotate(0.0f, -20.395f, 0.0f);
                prefab.transform.parent = parent;
                prefab.gameObject.transform.localScale = new Vector3(nine, nine, nine);
                prefab.gameObject.tag = "9";
            }
            //
            if (Hub.school.Count >= 10)
            {
                prefabTwo = Instantiate(prefab, new Vector3(9.32F, -2.67f, 3.34f), Quaternion.identity);
                prefabTwo.Rotate(0.0f, 159.605f, 0.0f);
                prefabTwo.transform.parent = parent;
                prefabTwo.gameObject.transform.localScale = new Vector3(ten, ten, ten);
                prefabTwo.gameObject.tag = "10";
            }
            //
            if (Hub.school.Count >= 11)
            {
                prefabThree = Instantiate(prefab, new Vector3(-9.622F, -2.67f, 3.511f), Quaternion.identity);
                prefabThree.Rotate(0.0f, 22.023f, 0.0f);
                prefabThree.transform.parent = parent;
                prefabThree.gameObject.transform.localScale = new Vector3(eleven, eleven, eleven);
                prefabThree.gameObject.tag = "11";
            }
            //
            if (Hub.school.Count >= 12)
            {
                prefabFour = Instantiate(prefab, new Vector3(9.24F, -2.67f, -3.91f), Quaternion.identity);
                prefabFour.Rotate(0.0f, 211.51f, 0.0f);
                prefabFour.transform.parent = parent;
                prefabFour.gameObject.transform.localScale = new Vector3(twelve, twelve, twelve);
                prefabFour.gameObject.tag = "12";
            }
            //
            if (Hub.school.Count >= 13)
            {
                prefabFive = Instantiate(prefab, new Vector3(-4.02F, -2.67f, 9.08f), Quaternion.identity);
                prefabFive.Rotate(0.0f, 70.0f, 0.0f);
                prefabFive.transform.parent = parent;
                prefabFive.gameObject.transform.localScale = new Vector3(thirteen, thirteen, thirteen);
                prefabFive.gameObject.tag = "13";
            }
            //
            if (Hub.school.Count >= 14)
            {
                prefabSix = Instantiate(prefab, new Vector3(-3.463F, -2.67f, -9.625f), Quaternion.identity);
                prefabSix.Rotate(0.0f, -67.977f, 0.0f);
                prefabSix.transform.parent = parent;
                prefabSix.gameObject.transform.localScale = new Vector3(fourteen, fourteen, fourteen);
                prefabSix.gameObject.tag = "14";
            }
            //
            if (Hub.school.Count >= 15)
            {
                prefabSeven = Instantiate(prefab, new Vector3(3.68F, -2.67f, 9.16f), Quaternion.identity);
                prefabSeven.Rotate(0.0f, 112.023f, 0.0f);
                prefabSeven.transform.parent = parent;
                prefabSeven.gameObject.transform.localScale = new Vector3(fifteen, fifteen, fifteen);
                prefabSeven.gameObject.tag = "15";
            }
            //
            if (Hub.school.Count == 16)
            {
                prefabEight = Instantiate(prefab, new Vector3(4.123F, -2.67f, -9.282f), Quaternion.identity);
                prefabEight.Rotate(0.0f, 252.623f, 0.0f);
                prefabEight.transform.parent = parent;
                prefabEight.gameObject.transform.localScale = new Vector3(sixteen, sixteen, sixteen);
                prefabEight.gameObject.tag = "16";
            }

        }


        void webGL()
        {
            leCamera.transform.position = new Vector3(0.0f, -0.702f, -4.09f);

            prefab = Instantiate(prefab, new Vector3(0.68F, -2.67f, -3.13f), Quaternion.identity);
            prefab.Rotate(0.0f, -90f, 0.0f);
            prefab.transform.parent = parent;
            prefab.gameObject.transform.localScale = new Vector3(10, 10, 10);
            prefab.gameObject.tag = "1";

            prefabTwo = Instantiate(prefab, new Vector3(-2.7F, -2.67f, -0.38f), Quaternion.identity);
            prefabTwo.Rotate(0.0f, 0.0f, 0.0f);
            prefabTwo.transform.parent = parent;
            prefabTwo.gameObject.transform.localScale = new Vector3(15, 15, 15);
            prefabTwo.gameObject.tag = "2";

            prefabThree = Instantiate(prefab, new Vector3(0.14F, -2.67f, 2.93f), Quaternion.identity);
            prefabThree.Rotate(0.0f, 90f, 0.0f);
            prefabThree.transform.parent = parent;
            prefabThree.gameObject.transform.localScale = new Vector3(8, 8, 8);
            prefabThree.gameObject.tag = "3";

            prefabFour = Instantiate(prefab, new Vector3(3.48F, -2.67f, 0.12f), Quaternion.identity);
            prefabFour.Rotate(0.0f, 180f, 0.0f);
            prefabFour.transform.parent = parent;
            prefabFour.gameObject.transform.localScale = new Vector3(9, 9, 9);
            prefabFour.gameObject.tag = "4";

        }


    }

}
