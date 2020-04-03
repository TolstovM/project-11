import { Role } from "./role";


export class User {
    email:string;
    name:string;
    roles: Array<Role>;
}