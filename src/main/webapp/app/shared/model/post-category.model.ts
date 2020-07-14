export interface IPostCategory {
  id?: number;
  name?: string;
  isEnabled?: boolean;
  maxImages?: number;
  validDays?: number;
  categoryId?: number;
}

export const defaultValue: Readonly<IPostCategory> = {
  isEnabled: false,
};
