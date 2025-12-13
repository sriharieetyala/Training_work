import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user';

@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [ReactiveFormsModule],
  providers: [FormBuilder],
  templateUrl: './user-edit.html',
  styleUrls: ['./user-edit.css']
})
export class UserEditComponent implements OnInit {

  id!: number;
  form!: FormGroup; // 👈 declare, don’t initialize here

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit() {
    // ✅ fb is available now
    this.form = this.fb.group({
      name: [''],
      email: ['']
    });

    this.id = Number(this.route.snapshot.paramMap.get('id'));

    this.userService.getUserById(this.id).subscribe(data => {
      this.form.patchValue(data);
    });
  }

  updateUser() {
    this.userService.updateUser(this.id, this.form.value)
      .subscribe(() => alert('User updated'));
  }
}
