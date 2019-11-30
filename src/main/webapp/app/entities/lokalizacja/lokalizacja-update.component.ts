import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILokalizacja, Lokalizacja } from 'app/shared/model/lokalizacja.model';
import { LokalizacjaService } from './lokalizacja.service';

@Component({
  selector: 'jhi-lokalizacja-update',
  templateUrl: './lokalizacja-update.component.html'
})
export class LokalizacjaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    lat: [],
    lng: []
  });

  constructor(protected lokalizacjaService: LokalizacjaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lokalizacja }) => {
      this.updateForm(lokalizacja);
    });
  }

  updateForm(lokalizacja: ILokalizacja) {
    this.editForm.patchValue({
      id: lokalizacja.id,
      lat: lokalizacja.lat,
      lng: lokalizacja.lng
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lokalizacja = this.createFromForm();
    if (lokalizacja.id !== undefined) {
      this.subscribeToSaveResponse(this.lokalizacjaService.update(lokalizacja));
    } else {
      this.subscribeToSaveResponse(this.lokalizacjaService.create(lokalizacja));
    }
  }

  private createFromForm(): ILokalizacja {
    return {
      ...new Lokalizacja(),
      id: this.editForm.get(['id']).value,
      lat: this.editForm.get(['lat']).value,
      lng: this.editForm.get(['lng']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILokalizacja>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
