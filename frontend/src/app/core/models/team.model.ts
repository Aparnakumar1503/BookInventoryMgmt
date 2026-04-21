export interface Teammate {
  readonly id: string;
  readonly name: string;
  readonly role: string;
  readonly moduleId: string;
  readonly moduleName: string;
  readonly modules: readonly string[];
  readonly username: string;
  readonly email: string;
}
