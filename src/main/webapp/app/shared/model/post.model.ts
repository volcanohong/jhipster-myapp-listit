import { Moment } from 'moment';
import { IImage } from 'app/shared/model/image.model';
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
  images?: IImage[];
}

export const defaultValue: Readonly<IPost> = {
  priceNegotiable: false,
};
