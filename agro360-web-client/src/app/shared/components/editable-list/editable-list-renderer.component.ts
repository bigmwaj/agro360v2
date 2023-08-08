import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { EditableListComponent } from './editable-list.component';
import { EditableListElement } from './editable-list.element';

@Component({
  selector: 'app-editable-list-renderer',
  templateUrl: './editable-list-renderer.component.html',
})
export class EditableListRendererComponent implements OnInit {
  constructor() { }

  @Input() 
  editableList:EditableListComponent<EditableListElement>;

  ngOnInit(): void {
  }
}
