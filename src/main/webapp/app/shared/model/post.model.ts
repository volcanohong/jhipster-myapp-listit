import { Moment } from 'moment';
import { ProductCondition } from 'app/shared/model/enumerations/product-condition.model';
import { PostStatus } from 'app/shared/model/enumerations/post-status.model';

export interface IPost {
  id?: number;
  name?: string;
  detail?: string;
  searchText?: string;
  price?: number;
  priceNegotiable?: boolean;
  condition?: ProductCondition;
  status?: PostStatus;
  createdDate?: string;
  lastModifiedDate?: string;
  lastReviewedData?: string;
  reviewedCount?: number;
  locationId?: number;
  categoryId?: number;
  userId?: number;
  imageId?: number;
}

export const defaultValue: Readonly<IPost> = {
  priceNegotiable: false,
};
