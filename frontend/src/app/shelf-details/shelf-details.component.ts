import { Component, OnInit } from '@angular/core';
import { InventoryService } from '../inventory.service';
import { ActivatedRoute } from '@angular/router';
import { NgIf } from "@angular/common";

@Component({
  selector: 'app-shelf-details',
  imports: [NgIf],
  templateUrl: './shelf-details.component.html',
  styleUrl: './shelf-details.component.scss'
})
export class ShelfDetailsComponent implements OnInit {
   shelfDetails :any = {};
   shelfId : number | null = null;

   constructor(private inventoryService : InventoryService, private route:ActivatedRoute){}

   ngOnInit() : void{
    const id = this.route.snapshot.paramMap.get('shelfId');
    this.shelfId = id ? +id : null

    if(this.shelfId){
      this.fetchShelfDetails(this.shelfId);
    }
   }

   fetchShelfDetails(shelfId : number){
     this.inventoryService.getShelfDetails(shelfId).subscribe(
      (data : any) => {
        this.shelfDetails = {
          shelfId: data.shelfId ,
          shelfName : data.shelfName,
          shelfType : data.shelfType ,
          shelfPositionId: data.shelfPositionId !== undefined && data.shelfPositionId !== null ? data.shelfPositionId : null,
          shelfPositionName: data.shelfPositionName ?? "N/A",
          deviceId: data.deviceId !== undefined && data.deviceId !== null ? data.deviceId : null,
          deviceName : data.deviceName ?? "N/A",
          deviceType: data.deviceType ?? "N/A"
        };
      },
      (error)=>{
        console.error('Error fetching shelf details :' ,error);
      }
     );
   }
}
