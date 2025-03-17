import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ShelfV0 } from './inventory/shelfv0.model'
import { ShelfPositionV0 } from './inventory/shelfPositionv0.model'
import { Observable } from 'rxjs';
import { ShelfDetailsV0 } from './shelf-details/shelfDetails.model';


@Injectable({
  providedIn: 'root'
})

export class InventoryService {
  private apiUrl = 'http://localhost:8080/api/inventory'
  constructor(private http: HttpClient) { }

  saveShelf(shelfV0: ShelfV0): Observable<ShelfV0> {
    return this.http.post<ShelfV0>(`${this.apiUrl}/shelf`, shelfV0);
  }

  getShelf(id: number): Observable<ShelfV0> {
    return this.http.get<ShelfV0>(`${this.apiUrl}/shelf/${id}`)
  }
  saveShelfPosition(shelfPositionV0: ShelfPositionV0): Observable<ShelfPositionV0> {
    return this.http.post<ShelfPositionV0>(`${this.apiUrl}/shelfPosition`, shelfPositionV0);
  }

  getShelfPosition(id: number): Observable<ShelfPositionV0> {
    return this.http.get<ShelfPositionV0>(`${this.apiUrl}/shelfPosition/${id}`);
  }

  deleteShelf(id: number): Observable<ShelfV0>{
    return this.http.delete<ShelfV0>(`${this.apiUrl}/shelf/${id}`);
  }

  deleteShelfPosition(id: number): Observable<ShelfPositionV0>{
    return this.http.delete<ShelfPositionV0>(`${this.apiUrl}/shelfPosition/${id}`);
  }

  addShelfPositionToDevice(deviceId: number, shelfPositionId: number) {
    return this.http.post(
        `${this.apiUrl}/relationship/device/shelfPosition`, 
        { deviceId, shelfPositionId }, 
        { responseType: 'text' } 
      )
  }
  
  
  addShelfToShelfPosition(shelfPositionId: number, shelfId: number): Observable<any> {
     return this.http.post(
      `${this.apiUrl}/relationship/shelfPosition/shelf`,
       { shelfPositionId , shelfId},
       { responseType : 'text' }
       )    
  }
  
  
  getAllShelves() {
    return this.http.get<ShelfV0[]>(`${this.apiUrl}/shelf`);
  }
  
  getAllShelfPositions() {
    return this.http.get<ShelfPositionV0[]>(`${this.apiUrl}/shelfPosition`);
  }
  
  getShelfDetails(shelfId : number) : Observable<any>{
    return this.http.get<ShelfDetailsV0[]>(`${this.apiUrl}/shelfDetails/${shelfId}`);
  }

}