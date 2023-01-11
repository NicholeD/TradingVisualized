//
//Copyright (c) 2022 All Rights Reserved
//Title: Trading Visualized
//Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett, Tanner Stahara, Zachary Chalmers
//

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Highlighter : MonoBehaviour
{
    public Material[] material;
    private Renderer renderer;
    private Renderer skinRender;
    public GameObject skin;
    public GameObject clickBlocker;
    public GameObject fish;
    private Material originalSkin;
    private bool selected;
    private bool highlighted;
    public Canvas fishCanvas;
    public Canvas actionCanvas;
    public int index;
    public Text name;
    public Text actioName;
    public Text size;
    public Text quantity;
    public Text price;
    public Text total;
    public Text status;
    public Dropdown fishToEat;
    public Button spearFish;
    public Button grenadeLaunch;
    public Text fishText;
    public GameObject bubleExpode;
    public GameObject grenadeObject;
    public GameObject spawnPoint;

    void Start()
    {
        clickBlocker = GameObject.Find("Blocker");
        renderer = GetComponent<Renderer>();
        renderer.enabled = false;
        highlighted = false;
        skinRender = skin.GetComponent<Renderer>();
        originalSkin = skinRender.material;
        fishCanvas.enabled = false;
        actionCanvas.enabled = false;
        index = System.Convert.ToInt32(this.transform.parent.tag) - 1;
        name.text = Hub.school[index].name;
        actioName.text = Hub.school[index].name;
        size.text = Hub.school[index].size.ToString();
        quantity.text = Hub.school[index].quantity.ToString();
        price.text = Hub.school[index].price.ToString("C");
        //Room for $9,999,999.99.99
        total.text = (Hub.school[index].price * Hub.school[index].quantity).ToString("C");
        fishToEat.onValueChanged.AddListener(EatFish);
        grenadeLaunch.onClick.AddListener(grenade);
        spearFish.onClick.AddListener(spear);

    }

    private void Update()
    {
        if (Input.GetKeyDown("escape"))
        {
            resetHighlight();
        }
    }
    private void spear()
    {
        //Find GameObject
        int ID = int.Parse(this.transform.parent.tag);
        fish = GameObject.FindGameObjectWithTag(ID.ToString());
        //Debug.Log("ID: " + ID + " Name: " + name.text);
        //Find LiveFish[]
        int indexF = findLF(name.text);
        //Debug.Log("ID: " + Hub.liveFish[indexF].id + " Name: " + Hub.liveFish[indexF].name);
        //Find School[]
        Fish liveF = Hub.liveFish[indexF];
        int indexS = findSchoolFish(liveF);
        //Debug.Log("ID: " + Hub.school[indexS].id + " Name:" + Hub.school[indexS].name);
        var cloneBubble = Instantiate(bubleExpode, new Vector3(fish.transform.position.x, fish.transform.position.y + .5f, fish.transform.position.z), Quaternion.identity);
        //Kill Fish
        Destroy(fish);
        Hub.fish -= 1;
        Hub.liveFish[indexF].status = "Deceased";
        Hub.school[indexS].status = "Deceased";
        //Debug.Log("Name: " + Hub.school[indexS].name + " Status: " + Hub.school[indexS].status);

        Hub.cash += Hub.school[indexS].price * Hub.school[indexS].quantity;
        resetHighlight();

        Hub.save();
    }

    public int findLF(string name) {
        for (int i = 0; i < Hub.liveFish.Count; i++)
        {
            if (Hub.liveFish[i].name == name) {
                return i;
            }
        }
        return 0;
    }
    public int findLFbyID(int id)
    {
        for (int i = 0; i < Hub.liveFish.Count; i++)
        {
            if (Hub.liveFish[i].id == id)
            {
                return i;
            }
        }
        return 0;
    }

    private void grenade()
    {
        int tag = int.Parse(this.transform.parent.tag);
        resetHighlight();
        spawnPoint = GameObject.FindGameObjectWithTag("Spawn");
        var cloneGrenade = Instantiate(grenadeObject, new Vector3(spawnPoint.transform.position.x, spawnPoint.transform.position.y, spawnPoint.transform.position.z), Quaternion.identity);
    }

    IEnumerator WaitFive(GameObject clone)
    {
        yield return new WaitForSeconds(5);
        Destroy(clone);
    }
    private void updateFishList()
    {
        deleteFishList();
        for (int i = 0; i < Hub.liveFish.Count; i++)
        {
            if (Hub.liveFish[i].id.ToString() != this.transform.parent.tag)
                if (Hub.liveFish[i].status != "Deceased")
                    fishToEat.options.Add(new Dropdown.OptionData() { text = Hub.liveFish[i].name });
        }
    }
    private void deleteFishList()
    {
        for (int i = 0; i < Hub.liveFish.Count; i++)
        {
            fishToEat.ClearOptions();
            fishToEat.options.Add(new Dropdown.OptionData() { text = "Pick A Fish!" });

        }
    }
    private void EatFish(int tag)
    {
        //Find OptionIndex
        int optionIndex = fishToEat.GetComponent<Dropdown>().value;
        //Debug.Log("Option Index: " + optionIndex);
        //Find GO Prey
        List<Dropdown.OptionData> menuOptions = fishToEat.GetComponent<Dropdown>().options;
        string preyName = menuOptions[optionIndex].text;
        //Debug.Log("Prey: " + preyName);
        //Find LiveFish[] Prey
        int indexPreyF = findLF(preyName);
        //Debug.Log("ID: " + Hub.liveFish[indexPreyF].id + " Name: " + Hub.liveFish[indexPreyF].name);
        //Find School[] Prey
        Fish prey = Hub.liveFish[indexPreyF];
        int indexPreyS = findSchoolFish(prey);
        //Debug.Log("ID: " + Hub.school[indexPreyS].id + " Name:" + Hub.school[indexPreyS].name);
        //Find GO Pred
        int ID = int.Parse(this.transform.parent.tag);
        GameObject predator = GameObject.FindGameObjectWithTag(ID.ToString());
        //Find LiveFish[]Pred
        int indexPredF = findLF(name.text);
        //Debug.Log("ID: " + Hub.liveFish[indexPredF].id + " Name: " + Hub.liveFish[indexPredF].name);
        //Find School[]Pred
        Fish predFish = Hub.liveFish[indexPredF];
        int indexPredS = findSchoolFish(predFish);
        //Debug.Log("ID: " + Hub.liveFish[indexPredS].id + " Name: " + Hub.liveFish[indexPredS].name);

        //Create GO Prey
        GameObject preyFish = GameObject.FindGameObjectWithTag(Hub.liveFish[indexPreyF].id.ToString());
        //Kill GO Prey
        var clone = Instantiate(bubleExpode, new Vector3(preyFish.transform.position.x, preyFish.transform.position.y + .5f, preyFish.transform.position.z), Quaternion.identity);
        Destroy(preyFish);
        Hub.liveFish[indexPreyF].status = "Deceased";
        Hub.school[indexPreyS].status = "Deceased";
        Hub.fish -= 1;
        //Update Cash Pre/Prey
        updatePredPrey(indexPredS, indexPreyS, indexPredF);
        //Update Size
        float size = Hub.school[index].size = Hub.setIndividualGameSive(indexPredF, indexPreyF);
        predator.gameObject.transform.localScale = new Vector3(size, size, size);
        var cloneBub = Instantiate(bubleExpode, new Vector3(predator.transform.position.x, predator.transform.position.y + 1f, predator.transform.position.z), Quaternion.identity);

        resetHighlight();

        Hub.setTotalValue();
        Hub.save();
    }
    public int findSchoolFish(Fish fish)
    {
        for (int i = 0; i < Hub.school.Count; i++)
        {
            if (Hub.school[i].name == fish.name)
                return i;
        }

        return 0;
    }
    private void updatePredPrey(int pred, int prey, int predlive)
    {
        float cash = Hub.school[prey].price * Hub.school[prey].quantity;
        float addShare = Hub.school[pred].price/cash;
        Hub.school[pred].quantity += addShare;
        Hub.liveFish[predlive].quantity += addShare;
    }
    private void updateliveFish(string name)
    {
        for (int i = 0; i < Hub.liveFish.Count; i++)
        {
            if (Hub.liveFish[i].name.ToString() == name.ToString())
            {
                Fish deadFish = Hub.liveFish[i];
                int tagIt = findSchoolFish(deadFish);
                Hub.school[tagIt].status = "Deceased";
                Hub.setTotalValue();
                Hub.save();
                Hub.liveFish.Remove(Hub.liveFish[i]);
            }
        }

    }
    public void resetHighlight()
    {
        highlighted = false;
        actionCanvas.enabled = false;
        Hub.actionActive = false;
    }

    private void OnTriggerEnter(Collider collision)
    {
        //Debug.Log(collision.name);
        if (collision.tag == "Trigger")
        {
            //Find GameObject
            int ID = int.Parse(this.transform.parent.tag);
            fish = GameObject.FindGameObjectWithTag(ID.ToString());
            Debug.Log(this.transform.parent.tag + " was hit!");
            //Find LiveFish[]
            int indexF = findLFbyID(ID);
            Debug.Log("ID: " + Hub.liveFish[indexF].id + " Name: " + Hub.liveFish[indexF].name);
            //Find School[]
            Fish deadFish = Hub.liveFish[indexF];
            int indexS = findSchoolFish(deadFish);
            Debug.Log("ID: " + Hub.school[indexS].id + " Name:" + Hub.school[indexS].name);
            //Kill Fish
            var cloneBubble = Instantiate(bubleExpode, new Vector3(fish.transform.position.x, fish.transform.position.y + .5f, fish.transform.position.z), Quaternion.identity);
            Destroy(fish);
            Hub.fish -= 1;
            Hub.liveFish[indexF].status = "Deceased";
            Hub.school[indexS].status = "Deceased";
            //Cash
            Hub.cash += Hub.school[indexS].price * Hub.school[indexS].quantity;
            resetHighlight();
            Hub.setTotalValue();
            Hub.save();

        }
    }

    private void OnMouseEnter()
    {
        fishCanvas.enabled = true;

    }
    private void OnMouseDown()
    {
        if (highlighted == false && Hub.actionActive == false)
        {
            highlighted = true;
            actionCanvas.enabled = true;
            Hub.actionActive = true;
            deleteFishList();
            updateFishList();
            //Debug.Log("Fish Count: " + Hub.liveFish.Count);
        }
        else if (highlighted == true)
        {
            skinRender.sharedMaterial = originalSkin;
            highlighted = false;
            actionCanvas.enabled = false;
            Hub.actionActive = false;
        }
    }

    private void OnMouseExit()
    {
        if (highlighted == false)
        {
            skinRender.sharedMaterial = originalSkin;
            fishCanvas.enabled = false;
        }
        else
            fishCanvas.enabled = false;

    }
    public int getFishTag(string fishTag)
    {
        int tag = 0;
        if (fishTag == "One")
            tag = 1;
        else if (fishTag == "Two")
            tag = 2;
        else if (fishTag == "Three")
            tag = 3;
        else if (fishTag == "Four")
            tag = 4;
        else if (fishTag == "Five")
            tag = 5;
        else if (fishTag == "Six")
            tag = 6;
        else if (fishTag == "Seven")
            tag = 7;
        else if (fishTag == "Eight")
            tag = 8;
        else if (fishTag == "Nine")
            tag = 9;
        else if (fishTag == "Ten")
            tag = 10;
        else if (fishTag == "Eleven")
            tag = 11;
        else if (fishTag == "Twelve")
            tag = 12;
        else if (fishTag == "Thirteen")
            tag = 13;
        else if (fishTag == "Fourteen")
            tag = 14;
        else if (fishTag == "Fifteen")
            tag = 15;
        else if (fishTag == "Sixteen")
            tag = 16;
        else if (fishTag == "Seventeen")
            tag = 17;
        else if (fishTag == "Eighteen")
            tag = 18;
        else if (fishTag == "Nineteen")
            tag = 19;
        else if (fishTag == "Twenty")
            tag = 20;
        return tag;
    }
}
