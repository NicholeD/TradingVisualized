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
        index = System.Convert.ToInt32(this.transform.parent.tag)-1;
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
        size.text = Hub.liveFish[index].size.ToString();
        quantity.text = Hub.liveFish[index].quantity.ToString();
        price.text = Hub.liveFish[index].price.ToString("C");
        total.text = (Hub.liveFish[index].price * Hub.liveFish[index].quantity).ToString("C");
    }
    private void spear() {
        int tag = int.Parse(this.transform.parent.tag);
        Debug.Log(name.text);
        fish = GameObject.FindGameObjectWithTag(tag.ToString());
        var cloneBubble = Instantiate(bubleExpode, new Vector3(fish.transform.position.x, fish.transform.position.y + .5f, fish.transform.position.z), Quaternion.identity);
        Destroy(fish);
        Hub.fish -= 1;
        Hub.cash += Hub.liveFish[tag].price * Hub.liveFish[tag].quantity;
        resetHighlight();
        StartCoroutine(WaitFive(cloneBubble));
        Hub.cash += Hub.liveFish[tag - 1].price * Hub.liveFish[tag - 1].quantity;
        Hub.liveFish[tag - 1].status = "Deceased";
        Hub.save();
    }

    private void grenade() {
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
    private void updateFishList() {
        deleteFishList();
        for (int i = 0; i < Hub.liveFish.Count; i++) {
            if (Hub.liveFish[i].id.ToString() != this.transform.parent.tag)
                if (Hub.liveFish[i].status != "Deceased")
                    fishToEat.options.Add(new Dropdown.OptionData() { text = Hub.liveFish[i].name });
        }
    }
    private void deleteFishList() {
        for (int i = 0; i < Hub.liveFish.Count; i++)
        {
            fishToEat.ClearOptions();
            fishToEat.options.Add(new Dropdown.OptionData() { text = "Pick A Fish!" });

        }
    }
    private void EatFish(int tag)
    {
        int optionIndex = fishToEat.GetComponent<Dropdown>().value;
        List<Dropdown.OptionData> menuOptions = fishToEat.GetComponent<Dropdown>().options;
        string fishName = menuOptions[optionIndex].text;
        for (int i = 0; i < Hub.liveFish.Count; i++) {
            if (Hub.liveFish[i].name == fishName) {
                Debug.Log("I found " + Hub.liveFish[i].name + " with id: " + Hub.liveFish[i].id);
                tag = Hub.liveFish[i].id;
            }
        }
        int AgressorTag = int.Parse(this.transform.parent.tag);
        //tag = getFishTag(fishTag);
        GameObject[] destroyObject;
        destroyObject = GameObject.FindGameObjectsWithTag((tag).ToString());
        GameObject monsterFish = GameObject.FindGameObjectWithTag((AgressorTag).ToString());
        foreach (GameObject oneObject in destroyObject)
        {
            var clone = Instantiate(bubleExpode, new Vector3(oneObject.transform.position.x, oneObject.transform.position.y + .5f, oneObject.transform.position.z), Quaternion.identity);
            Destroy(oneObject);
            Hub.fish -= 1;
            Fish fish = Hub.liveFish[tag - 1];
            int schoolTag = findSchoolFish(fish);
            Hub.school[schoolTag].status = "Deceased";
            Debug.Log(Hub.school[schoolTag].name);
            Hub.setTotalValue();
            resetHighlight();
            StartCoroutine(WaitFive(clone));

        }
       
        updatePredPrey(index, tag - 1);
        float size = Hub.school[index].size = Hub.setIndividualGameSive(index, tag - 1);
        monsterFish.gameObject.transform.localScale = new Vector3(size,size,size);
        var cloneBub = Instantiate(bubleExpode, new Vector3(monsterFish.transform.position.x, monsterFish.transform.position.y + 1f, monsterFish.transform.position.z), Quaternion.identity);
        Hub.setTotalValue();
        Hub.save();
    }
    public int findSchoolFish(Fish fish) {
        for (int i = 0; i < Hub.school.Count; i++) {
            if (Hub.school[i].name == fish.name)
                return i;
        }
        
        return 0;
    }
    private void updatePredPrey(int pred, int prey) {
        float addedShares = (Hub.liveFish[pred].price * Hub.liveFish[prey].quantity) / Hub.liveFish[pred].price;
        Hub.liveFish[pred].quantity += addedShares;
    }
    private void updateliveFish(string name) {
        for (int i = 0; i < Hub.liveFish.Count; i++) {
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
    public void resetHighlight() {
        highlighted = false;
        actionCanvas.enabled = false;
        Hub.actionActive = false;
    }

    private void OnTriggerEnter(Collider collision)
    {
        //Debug.Log(collision.name);
        if (collision.tag == "Trigger") {
            Debug.Log(this.transform.parent.tag + " was hit!");
            int indexF = int.Parse(this.transform.parent.tag);
            GameObject fish = this.transform.parent.gameObject;
            var clone = Instantiate(bubleExpode, new Vector3(fish.transform.position.x, fish.transform.position.y + .5f, fish.transform.position.z), Quaternion.identity);
            Destroy(fish);
            Hub.cash += Hub.liveFish[indexF].price * Hub.liveFish[indexF].quantity;
            Hub.liveFish[indexF].status = "Deceased";
            Fish smallFry = Hub.liveFish[indexF];
            int taggert = findSchoolFish(smallFry);
            Hub.school[taggert].status = "Deceased";
            Debug.Log(Hub.school[taggert-1].name + " is dead.");
            //updateliveFish(name.text);
            Hub.fish -= 1;
            //Hub.save();
        }
    }

        private void OnMouseEnter()
    {
        fishCanvas.enabled = true;

    }
    private void OnMouseDown() {
        if (highlighted == false && Hub.actionActive == false) {
            highlighted = true;
            actionCanvas.enabled = true;
            Hub.actionActive = true;
            deleteFishList();
            updateFishList();
            Debug.Log("Fish Count: " + Hub.liveFish.Count);
        }
        else if(highlighted == true)
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
