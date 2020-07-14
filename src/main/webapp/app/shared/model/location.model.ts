export interface ILocation {
  id?: number;
  address?: string;
  postCode?: string;
  cityId?: number;
}

export const defaultValue: Readonly<ILocation> = {};
