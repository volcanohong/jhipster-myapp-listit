export interface IImage {
  id?: number;
  url?: string;
  contentContentType?: string;
  content?: any;
  isTop?: boolean;
  postId?: number;
}

export const defaultValue: Readonly<IImage> = {
  isTop: false,
};
