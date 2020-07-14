import { Moment } from 'moment';

export interface IUserProfile {
  id?: number;
  phone?: string;
  emailEnabled?: boolean;
  smsEnabled?: boolean;
  lastLoginDate?: string;
  lastLoginIp?: number;
  isLocked?: boolean;
  lockDate?: string;
  maxImages?: number;
  validDays?: number;
  isPrivacyEnabled?: boolean;
  userId?: number;
  locationId?: number;
}

export const defaultValue: Readonly<IUserProfile> = {
  emailEnabled: false,
  smsEnabled: false,
  isLocked: false,
  isPrivacyEnabled: false,
};
