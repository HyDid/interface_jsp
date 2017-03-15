//
//  ListTableViewController.swift
//  swift私人通讯录
//
//  Created by LiHongYu on 2017/3/15.
//  Copyright © 2017年 LiHongYu. All rights reserved.
//

import UIKit

class ListTableViewController: UITableViewController {

    @IBAction func add(_ sender: AnyObject) {
        
        performSegue(withIdentifier: "listtodetail", sender: nil)
    }
    
    var personList = [Person]()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

       
        
        loadData { (list) in
            print(list)
            
            self.personList += list
            
            self.tableView.reloadData()
            
        }
    }

    
    private func loadData(completion:@escaping (_ list:[Person])->()) -> () {
        
        DispatchQueue.global().async {
            print("正在努力加载")
            
            Thread.sleep(forTimeInterval: 1)
            
            var arrayM = [Person]()
            
            for i in 0..<20{
                
                let p = Person()
                
                p.name = "zhangsan - \(i)"
                p.phone = "1860" + String(format:"%06d",arc4random_uniform(1000000))
                p.title = "boss"
                
                arrayM.append(p)
            }
            
            
            DispatchQueue.main.async(execute: { 
                completion(arrayM)
            })
            
            
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        let vc = segue.destination as! DetailViewTableViewController
        
        if let IndexPath = sender as? IndexPath {
            vc.person = personList[IndexPath.row]
            
            vc.completionCallBack = {
                self.tableView.reloadRows(at: [IndexPath], with: .automatic)
            }
        }else{
            
            vc.completionCallBack = { [weak vc] in
                
                guard let p = vc?.person else {
    
                    return
                }
                self.personList.insert(p, at: 0)
                
                self.tableView.reloadData()
            }
            
        }
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        performSegue(withIdentifier: "listtodetail", sender: indexPath)
    }
    
    
       // MARK: - Table view data source



    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return personList.count
    }

  
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cellId", for: indexPath)

        cell.textLabel?.text = personList[indexPath.row].name
        cell.detailTextLabel?.text = personList[indexPath.row].phone


        return cell
    }
 

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
