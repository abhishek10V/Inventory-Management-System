import { NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Component, inject, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InventoryService } from '../inventory.service';
import { ShelfPositionV0 } from './shelfPositionv0.model';
import { ShelfV0 } from './shelfv0.model';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { ShelfDetailsV0 } from '../shelf-details/shelfDetails.model';

@Component({
  selector: 'app-inventory',
  standalone: true,
  imports: [FormsModule, NgFor, RouterLink],
  templateUrl: './inventory.component.html',
  styleUrl: './inventory.component.scss',
})
export class InventoryComponent implements OnDestroy {
  private toastr = inject(ToastrService);
  private subscriptions: Subscription[] = []; 

  constructor(private inventoryService: InventoryService) {}

  showForm = false;
  deviceId: number = NaN;
  shelfv0Id: number = NaN;
  shelfPositionv0Id: number = NaN;
  shelfv0: ShelfV0 = { id: NaN, name: '', shelfType: '', shelfPositionId: NaN };
  shelfPositionv0: ShelfPositionV0 = { id: NaN, name: '', deviceId: NaN };
  shelves: ShelfV0[] = [];
  shelfPositions: ShelfPositionV0[] = [];
 

  showToastSuccess(message: string) {
    this.toastr.success(message, 'Success');
  }

  showToastError(message: string) {
    this.toastr.error(message, 'Error');
  }

  hideform() {
    this.showForm = false;
    this.deviceId = NaN;
    this.shelfv0Id = NaN;
    this.shelfPositionv0Id = NaN;
    this.shelfv0 = { id: NaN, name: '', shelfType: '', shelfPositionId: NaN };
    this.shelfPositionv0 = { id: NaN, name: '', deviceId: NaN };
  }

  onSubmitSaveShelf() {
    const sub = this.inventoryService.saveShelf(this.shelfv0).subscribe(
      (response) => {
        console.log('Shelf saved: ', response);
        this.showToastSuccess('Shelf saved successfully!');
        this.fetchAllShelves();
        this.hideform();
      },
      (error) => {
        console.error('Error saving shelf: ', error);
        this.showToastError('Error saving shelf!');
      }
    );
    this.subscriptions.push(sub);
  }

  onSubmitSaveShelfPosition() {
    const sub = this.inventoryService.saveShelfPosition(this.shelfPositionv0).subscribe(
      (response) => {
        console.log('Saved Shelf Position ', response);
        this.showToastSuccess('Shelf Position saved successfully!');
        this.fetchAllShelfPositions();
        this.hideform();
      },
      (error) => {
        console.error('Error saving Shelf Position ', error);
        this.showToastError('Error saving Shelf Position!');
      }
    );
    this.subscriptions.push(sub);
  }

  onSubmitGetShelf() {
    const sub = this.inventoryService.getShelf(this.shelfv0Id).subscribe(
      (response) => {
        console.log('Shelf retrieved', response);
        this.showToastSuccess('Shelf retrieved successfully!');
        this.hideform();
      },
      (error) => {
        console.error('Error fetching shelf ', error);
        this.showToastError('Error fetching shelf!');
      }
    );
    this.subscriptions.push(sub);
  }

  onSubmitGetShelfPosition() {
    const sub = this.inventoryService.getShelfPosition(this.shelfPositionv0Id).subscribe(
      (response) => {
        console.log('ShelfPosition retrieved: ', response);
        this.showToastSuccess('Shelf Position retrieved successfully!');
        this.hideform();
      },
      (error) => {
        console.error('Error fetching Shelf Position', error);
        this.showToastError('Error fetching Shelf Position!');
      }
    );
    this.subscriptions.push(sub);
  }

  onSubmitDeleteShelf(){
    const sub = this.inventoryService.deleteShelf(this.shelfv0Id).subscribe(
      (response) => {
        console.log("Shelf Deleted", response);
        this.showToastSuccess("Shelf deleted successfully!");
        this.fetchAllShelves();
        this.hideform();
      },
      (error) => {
        console.error("Error deleting shelf", error);
        this.showToastError("Error deleting shelf!");
      }
    );
    this.subscriptions.push(sub); 
  }

  onSubmitDeleteShelfPosition(){
    const sub = this.inventoryService.deleteShelfPosition(this.shelfPositionv0Id).subscribe(
      (response) => {
        console.log("Shelf Position Deleted", response);
        this.showToastSuccess("Shelf Position deleted successfully!");
        this.fetchAllShelfPositions();
        this.hideform();
      },
      (error) => {
        console.error("Error deleting shelf position", error);
        this.showToastError("Error deleting shelf position!");
      }
    );
    this.subscriptions.push(sub); 
  }

  onSubmitAddShelfPositonToDevice() {
    const sub = this.inventoryService.addShelfPositionToDevice(this.deviceId, this.shelfPositionv0Id).subscribe(
      (response) => {
        console.log(`Added ShelfPosition with shelfPositionId: ${this.shelfPositionv0Id} to deviceId: ${this.deviceId}`, response);
        this.showToastSuccess('Shelf Position added to device successfully!');
        this.fetchAllShelfPositions();
        this.hideform();
      },
      (error) => {
        console.error('Error Adding shelf position to device', error);
        this.showToastError('Error adding Shelf Position to device!');
      }
    );
    this.subscriptions.push(sub);
  }

  onSubmitAddShelfToShelfPositon() {
    const sub = this.inventoryService.addShelfToShelfPosition(this.shelfPositionv0Id, this.shelfv0Id).subscribe(
      (response) => {
        console.log(`Added Shelf with shelfId: ${this.shelfv0Id} to Shelf Position with shelfPositionId: ${this.shelfPositionv0Id}`, response);
        this.showToastSuccess('Shelf added to Shelf Position successfully!');
        this.fetchAllShelves();
        this.hideform();
      },
      (error) => {
        console.error('Error adding shelf to shelf position', error);
        this.showToastError('Error adding shelf to Shelf Position!');
      }
    );
    this.subscriptions.push(sub);
  }

  fetchAllShelves() {
    const sub = this.inventoryService.getAllShelves().subscribe(
      (response: ShelfV0[]) => {
        this.shelves = response;
        console.log('Shelves fetched successfully:', this.shelves);
        this.showToastSuccess('Shelves fetched successfully!');
      },
      (error) => {
        console.error('Error fetching shelves:', error);
        this.showToastError('Error fetching shelves!');
      }
    );
    this.subscriptions.push(sub);
  }

  fetchAllShelfPositions() {
    const sub = this.inventoryService.getAllShelfPositions().subscribe(
      (response: ShelfPositionV0[]) => {
        this.shelfPositions = response;
        console.log('Shelf Positions fetched successfully:', this.shelfPositions);
        this.showToastSuccess('Shelf Positions fetched successfully!');
      },
      (error) => {
        console.error('Error fetching shelf positions:', error);
        this.showToastError('Error fetching Shelf Positions!');
      }
    );
    this.subscriptions.push(sub);
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
    console.log("All subscriptions have been unsubscribed.");
  }
}